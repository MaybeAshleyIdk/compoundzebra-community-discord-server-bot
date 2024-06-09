package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVal
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.quoted
import kotlinx.coroutines.sync.Mutex
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import java.math.BigDecimal
import java.time.Duration
import javax.inject.Inject
import kotlin.random.Random

public class SelfTimeoutServiceImpl @Inject constructor(
	private val logger: Logger,
) : SelfTimeoutService {

	private companion object {

		val INITIAL_TIMEOUT_DURATION: Duration = Duration.ofSeconds(5)

		val TIMEOUT_MULTIPLIER: BigDecimal = BigDecimal("1.25")

		const val MEGA_TIMEOUT_CHANCE: Float = 0.005f
		val MEGA_TIMEOUT_DURATION_MIN: Duration = Duration.ofHours(6)
		val MEGA_TIMEOUT_DURATION_MAX: Duration = Duration.ofHours(12)
	}

	private class SelfTimeoutMemberInfo {

		val mutex: Mutex = Mutex()

		@Suppress("RemoveRedundantQualifierName")
		var currentDuration: Duration = SelfTimeoutServiceImpl.INITIAL_TIMEOUT_DURATION
	}

	private val infoMap: AtomicVal<MutableMap<GuildMemberKey, SelfTimeoutMemberInfo>> = AtomicVal(HashMap())

	override suspend fun timeOutMember(member: Member) {
		val guild: Guild = member.guild

		if (!(guild.selfMember.hasPermission(Permission.MODERATE_MEMBERS))) {
			val msg = "Bot lacks the permission ${Permission.MODERATE_MEMBERS.getName().quoted()} to time out " +
				"the member @${member.user.name} (${member.user.id})"
			this.logger.logWarning(msg)

			return
		}

		if (!(guild.selfMember.canInteract(member))) {
			this.logger.logInfo("Bot cannot interact with the member @${member.user.name} (${member.user.id})")
			return
		}

		val memberKey: GuildMemberKey = GuildMemberKey.ofMember(member)

		val memberInfo: SelfTimeoutMemberInfo = this.infoMap
			.visit { infoMap: MutableMap<GuildMemberKey, SelfTimeoutMemberInfo> ->
				infoMap.computeIfAbsent(memberKey) { SelfTimeoutMemberInfo() }
			}

		if (!(memberInfo.mutex.tryLock())) {
			val msg = "Mutex of member @${member.user.name} (${member.user.id}) (guild ID: ${member.guild.id}) is " +
				"already locked (self-timeout)"
			this.logger.logWarning(msg)

			return
		}

		try {
			@Suppress("RemoveRedundantQualifierName")
			if (Random.nextFloat() < SelfTimeoutServiceImpl.MEGA_TIMEOUT_CHANCE) {
				this.doMegaTimeout(member)
			} else {
				memberInfo.currentDuration = this.doNormalTimeout(member, memberInfo.currentDuration)
			}
		} finally {
			memberInfo.mutex.unlock()
		}
	}

	private suspend fun doMegaTimeout(member: Member) {
		@Suppress("RemoveRedundantQualifierName")
		val megaTimeoutDurationRange: ClosedRange<Duration> =
			SelfTimeoutServiceImpl.MEGA_TIMEOUT_DURATION_MIN..SelfTimeoutServiceImpl.MEGA_TIMEOUT_DURATION_MAX

		val megaTimeoutDuration: Duration = megaTimeoutDurationRange.random()

		this.timeOutMember(member, megaTimeoutDuration)

		val logMsg = "Timed out member @${member.user.name} (${member.user.id}) (guild ID: ${member.guild.id}) with " +
			"a MEGA timeout of $megaTimeoutDuration (self-timeout)"
		this.logger.logInfo(logMsg)
	}

	private suspend fun doNormalTimeout(member: Member, currentDuration: Duration): Duration {
		this.timeOutMember(member, currentDuration)

		@Suppress("RemoveRedundantQualifierName")
		val nextTimeoutDuration: Duration = (currentDuration * SelfTimeoutServiceImpl.TIMEOUT_MULTIPLIER)

		val logMsg = "Timed out member @${member.user.name} (${member.user.id}) (guild ID: ${member.guild.id}) " +
			"with a timeout of $currentDuration (self-timeout). " +
			"Next timeout for this member will be $nextTimeoutDuration"
		this.logger.logInfo(logMsg)

		return nextTimeoutDuration
	}

	private suspend fun timeOutMember(member: Member, timeoutDuration: Duration) {
		member.guild.timeoutFor(member, timeoutDuration)
			.reason("Self-timeout")
			.await()
	}
}

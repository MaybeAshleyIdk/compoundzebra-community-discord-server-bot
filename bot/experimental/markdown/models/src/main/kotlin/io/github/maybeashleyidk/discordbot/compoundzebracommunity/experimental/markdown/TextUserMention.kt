package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

@JvmInline
public value class UserMention(private val userId: ULong) : TextElement {

	private companion object {

		// mirroring the official Discord client's behavior
		const val UNKNOWN_USER_NAME: String = "unknown-user"
	}

	override fun toRawString(): String {
		return "<@${this.userId}>"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		val userName: String =
			when (channelContext) {
				is PrivateChannel -> {
					val user: User = channelContext.retrieveUser().await()
					this.getMentionedUserNameInPrivateChannel(user)
				}

				is GuildChannel -> {
					val member: Member? = channelContext.guild.retrieveMemberById(this.userId)
					member?.effectiveName
				}

				else -> null
			}
				?: Companion.UNKNOWN_USER_NAME

		return "@${userName}"
	}

	private suspend fun getMentionedUserNameInPrivateChannel(user: User): String? {
		if (user.idLong == this.userId.toLong()) {
			return user.effectiveName
		}

		val mentionedUser: User? = user.mutualGuilds.asFlow()
			.mapNotNull { mutualGuild: Guild ->
				mutualGuild.retrieveMemberById(this@UserMention.userId)
					?.user
			}
			.firstOrNull()

		if (mentionedUser != null) {
			return mentionedUser.effectiveName
		}

		return null
	}
}

private suspend fun Guild.retrieveMemberById(id: ULong): Member? {
	val memberList: List<Member> = this.retrieveMembersByIds(id.toLong()).await()
	return memberList.firstOrNull()
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.scheduledmessages

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ScheduledMessage
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.MonthDay
import java.time.ZoneOffset
import java.util.TreeMap
import javax.inject.Inject
import kotlin.concurrent.Volatile
import net.dv8tion.jda.api.JDA as Jda

public class ScheduledMessagesManagerImpl @Inject internal constructor(
	private val scheduledMessagesRunnableFactory: ScheduledMessagesRunnable.Factory,
) : ScheduledMessagesManager {

	private companion object {

		val THREAD_JOIN_TIMEOUT: Duration = Duration.ofSeconds(5)
	}

	private var runnableAndThreadPair: Pair<ScheduledMessagesRunnable, Thread>? = null

	@Synchronized
	override fun start(jda: Jda) {
		check(this.runnableAndThreadPair == null) {
			"Scheduled message manager is already active"
		}

		val scheduledMessagesRunnable: ScheduledMessagesRunnable = this.scheduledMessagesRunnableFactory.create(jda)
		val runnableAndThreadPair: Pair<ScheduledMessagesRunnable, Thread> =
			scheduledMessagesRunnable to Thread(scheduledMessagesRunnable)

		runnableAndThreadPair.second.start()

		this.runnableAndThreadPair = runnableAndThreadPair;
	}

	@Synchronized
	override fun stop() {
		val (runnable: ScheduledMessagesRunnable, thread: Thread) = this.runnableAndThreadPair
			?: error("Scheduled message manager is not active")

		runnable.stop()

		@Suppress("RemoveRedundantQualifierName")
		thread
			.join(
				ScheduledMessagesManagerImpl.THREAD_JOIN_TIMEOUT.toMillis(),
				(ScheduledMessagesManagerImpl.THREAD_JOIN_TIMEOUT.toNanosPart() / 1000),
			)

		if (thread.isAlive) {
			@Suppress("DEPRECATION")
			thread.stop()
		}
	}
}

internal class ScheduledMessagesRunnable @AssistedInject constructor(
	@Assisted private val jda: Jda,
	private val configSupplier: ConfigSupplier,
	private val logger: Logger,
) : Runnable {

	@AssistedFactory
	fun interface Factory {

		fun create(jda: Jda): ScheduledMessagesRunnable
	}

	private data class ScheduledMessageDetails(
		val guildSnowflakeId: String,
		val channelSnowflakeId: String,
		val messageContent: String,
	)

	@Volatile
	private var active: Boolean = true

	override fun run() {
		val config: Config = this.configSupplier.get()

		val details: MutableMap<LocalTime, Set<ScheduledMessageDetails>> = TreeMap()

		for ((guildSnowflakeId: String, scheduledMessages: Set<ScheduledMessage>) in config.scheduledMessages) {
			for (scheduledMessage: ScheduledMessage in scheduledMessages) {
				details
					.merge(
						scheduledMessage.utcTime,
						setOf(
							ScheduledMessageDetails(
								guildSnowflakeId = guildSnowflakeId,
								channelSnowflakeId = scheduledMessage.channelSnowflakeId,
								messageContent = scheduledMessage.messageContent,
							),
						),
					) { oldSet: Set<ScheduledMessageDetails>, additionalDetails: Set<ScheduledMessageDetails> ->
						oldSet + additionalDetails
					}
			}
		}



		if (details.isEmpty()) {
			return
		}

//		while (this.active) {
		val earliestEntryInTheFuture: Map.Entry<LocalTime, Set<ScheduledMessageDetails>> =
			details.findEarliestEntryInTheFuture()

		this.logger.logDebug("earliestEntryInTheFuture: $earliestEntryInTheFuture")
//		}
	}

	fun stop() {
		this.active = false
	}
}

private fun <V> Map<LocalTime, V>.findEarliestEntryInTheFuture(): Map.Entry<LocalTime, V> {
	val now: LocalTime = LocalTime.now(ZoneOffset.UTC)

	// given the follow list: [ 11:15, 13:30, 15:45 ]
	// and it is currently 14:00

	val timeList: List<Pair<LocalTime, Boolean>> = (this.keys.toList().map { it to false } + (now to true))
		.sortedBy(Pair<LocalTime, Boolean>::first)

	val nowIndex: Int = timeList.indexOfFirst(Pair<LocalTime, Boolean>::second)
	check(nowIndex != -1)

	val x: LocalTime =
		if (nowIndex != timeList.lastIndex) {
			timeList[nowIndex + 1].first
		} else {
			timeList
				.first { !(it.second) }
				.first
		}

//	check(this is SortedMap)
//
//	LocalDate.now(ZoneOffset.UTC)
//	val today: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
//	val now: LocalTime = LocalTime.now(ZoneOffset.UTC)
//
//	var foundLatestTimeInThePast = false
//
//	var latestTimeInThePast: LocalTime? = null
//
//	for (entry: Map.Entry<LocalTime, V> in this) {
//		if (latestTimeInThePast == null) {
//			latestTimeInThePast = entry.key
//			continue
//		}
//
//		if (latestTimeInThePast < entr)
//
//		if (!foundLatestTimeInThePast) {
//			val scheduledTime: LocalTime = entry.key
//			foundLatestTimeInThePast = (scheduledTime < today.toLocalTime())
//			continue
//		}
//
//		return entry
//	}
//
//	val firstKey: LocalTime = this.firstKey()
//	return this.entries
//		.first { (key: LocalTime, _: V) ->
//			key == firstKey
//		}
}

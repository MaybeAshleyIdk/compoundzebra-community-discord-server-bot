package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.message.calculateScoreOf
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.scoretypes.Score
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.scoretypes.Score.Companion.score
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import javax.inject.Inject

/*
 * To determine which messages count as "recent", the following logic is used:
 *
 * Each message and "message in-between" are given a score.
 * This score generally is supposed to represent the "height" of the item in the Discord client.
 */

public class RecentMessagesServiceImpl @Inject constructor(
	private val logger: Logger,
) : RecentMessagesService {

	private companion object {

		/**
		 * take at most this amount of messages
		 */
		const val MAX_POTENTIAL_MESSAGES_COUNT: Int = 25

		/**
		 * use this amount of messages to calculate score
		 *
		 * Rationale behind the +40: on average, around 40 messages gets sent in 8 minutes (see joint messages)
		 */
		const val MAX_SCORE_CALCULATION_MESSAGE_COUNT: Int = this.MAX_POTENTIAL_MESSAGES_COUNT + 40

		/**
		 * oldest message that hits the score still gets taken, anything older not anymore
		 */
		val MAX_SCORE: Score = 900.score
	}

	override fun getRecentMessagesOf(channel: MessageChannel): Sequence<Message> {
		return sequence {
//			val messages = channel.iterableHistory.takeAsync(Companion.MAX_SCORE_CALCULATION_MESSAGE_COUNT).await()
			val potentialMessages: Sequence<Message> = channel.iterableHistory
				.asSequence()
				.take(Companion.MAX_POTENTIAL_MESSAGES_COUNT)

			var currentScore: Score = 0.score

			for (potentialMessage: Message in potentialMessages) {
				val messageScore: Score = calculateScoreOf(potentialMessage)
				logger.logDebug("$messageScore - \"${potentialMessage.contentRaw}\"")
				currentScore += messageScore

				this@sequence.yield(potentialMessage)

				if (currentScore >= Companion.MAX_SCORE) {
					break
				}
			}
		}
	}
}

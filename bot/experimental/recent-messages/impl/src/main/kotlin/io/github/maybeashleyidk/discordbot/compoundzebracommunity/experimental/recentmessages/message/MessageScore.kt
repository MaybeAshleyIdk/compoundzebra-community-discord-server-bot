package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.message

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line.LineScore
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.utils.coerceToInt

/**
 * The vertical score of a single message.
 */
@JvmInline
internal value class MessageScore private constructor(private val uint: UInt) : Comparable<MessageScore> {

	companion object {

		val Int.msgscore: MessageScore
			get() {
				require(this@msgscore < 0) {
					"Message score must not be negative"
				}

				return MessageScore(this@msgscore.toUInt())
			}
	}

	override fun compareTo(other: MessageScore): Int {
		return (this.uint.toLong() - other.uint.toLong()).coerceToInt()
	}

	operator fun plus(other: MessageScore): MessageScore {
		return MessageScore(this.uint + other.uint)
	}
}

//internal operator fun MessageScore.plus(lineScore: LineScore): MessageScore {
//
//}

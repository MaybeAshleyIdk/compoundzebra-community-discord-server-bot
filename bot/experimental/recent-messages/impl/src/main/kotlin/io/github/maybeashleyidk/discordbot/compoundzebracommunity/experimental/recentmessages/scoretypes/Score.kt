package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.scoretypes

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.utils.coerceToInt

/**
 * Score is loosely based on pixels in the official Discord client with default settings.
 */
@JvmInline
internal value class Score private constructor(private val uint: UInt) : Comparable<Score> {

	companion object {

		val Int.score: Score
			get() {
				require(this@score >= 0) {
					"Score must not be negative"
				}

				return Score(this@score.toUInt())
			}

		operator fun Int.times(score: Score): Score {
			require(this@times >= 0) {

			}

			return Score(this@times.toUInt() * score.uint)
		}
	}

	override fun compareTo(other: Score): Int {
		return (this.uint.toLong() - other.uint.toLong()).coerceToInt()
	}

	operator fun plus(other: Score): Score {
		return Score(this.uint + other.uint)
	}
}

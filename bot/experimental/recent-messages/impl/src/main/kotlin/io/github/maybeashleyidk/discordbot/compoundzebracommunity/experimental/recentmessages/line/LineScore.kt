package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.utils.coerceToInt

/**
 * The horizontal score of a single line, which also includes automatic line breaks.
 */
@JvmInline
internal value class LineScore private constructor(private val uint: UInt) : Comparable<LineScore> {

	companion object {

		@Suppress("SpellCheckingInspection")
		val Int.lnscore: LineScore
			get() {
				require(this@lnscore >= 0) {
					"Line score must not be negative"
				}

				return LineScore(this@lnscore.toUInt())
			}

		fun avg(a: LineScore, b: LineScore): LineScore {
			return LineScore((a.uint + b.uint) / 2u)
		}

		operator fun UInt.times(lineScore: LineScore): LineScore {
			return LineScore(this@times * lineScore.uint)
		}
	}

	override fun compareTo(other: LineScore): Int {
		return (this.uint.toLong() - other.uint.toLong()).coerceToInt()
	}

	operator fun plus(other: LineScore): LineScore {
		return LineScore(this.uint + other.uint)
	}

	operator fun div(other: LineScore): UInt {
		return (this.uint / other.uint)
	}
}

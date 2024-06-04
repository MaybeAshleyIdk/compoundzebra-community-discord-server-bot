package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line.LineScore.Companion.avg
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line.LineScore.Companion.lnscore
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line.LineScore.Companion.times

private object Scores {

	object Char {

		val Narrow: LineScore = 4.lnscore
		val Wide: LineScore = 12.lnscore
		val Default: LineScore = avg(this.Narrow, this.Wide)
	}

	val LineBreak: LineScore = 2.lnscore
}

private val MAX_LINE_WIDTH_SCORE: LineScore = 90.lnscore

internal fun calculateScoreOf(line: String): LineScore {
	require('\n' !in line)

	val totalScoreWithoutLineBreaks: LineScore = line.map(::calculateScoreOf).reduce(LineScore::plus)
	val lineBreaksCount: UInt = (totalScoreWithoutLineBreaks / MAX_LINE_WIDTH_SCORE)
	val lineBreaksScore: LineScore = (lineBreaksCount * Scores.LineBreak)

	return (totalScoreWithoutLineBreaks + lineBreaksScore)
}

private fun calculateScoreOf(ch: Char): LineScore {
	return when {
		ch.isNarrow() -> Scores.Char.Narrow
		ch.isWide() -> Scores.Char.Wide
		else -> Scores.Char.Default
	}
}

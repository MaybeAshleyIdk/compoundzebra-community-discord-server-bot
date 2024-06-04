package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.message

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line.calculateScoreOf
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.message.MessageScore.Companion.msgscore
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.scoretypes.Score
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.scoretypes.Score.Companion.score
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.scoretypes.Score.Companion.times
import net.dv8tion.jda.api.entities.Message
import kotlin.time.times

// TODO: * replies
//       * attachments (incl. images)
//       * general markdown parsing

// joint messages are 8 minutes from the first message being sent?

// small emoji size: 22x22
// big emoji size: 48x48
// if message has more than 30 emojis -> use small emojis
// if message contains non-whitespace chars -> use small emojis

private object Scores {

	val Line: MessageScore = 20.msgscore
	val LineSpacing: MessageScore = 2.msgscore

	val ReactionLine = 30.score
}
// width of reactions is based on total digits of all reactions
// > 40 digits -> second line of reactions

internal fun calculateScoreOf(message: Message): Score {
	val l = message.contentStripped
		.lineSequence()
		.map {
			20.score
		}
		.toList()

	val lineSpacingScore: Score = (l.size - 1).coerceAtLeast(0) * 2.score

	return l.reduce(Score::plus) + lineSpacingScore
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionValue
import net.dv8tion.jda.api.entities.Member

public class PollManagerModifier(private val pollManager: PollManager) : PollModifier {

	override suspend fun voteOption(pollId: PollId, voterMember: Member, optionValue: PollOptionValue): PollDetails? {
		return this.pollManager.voteOption(pollId, voterMember, optionValue)
	}

	override suspend fun closePollIfAllowed(pollId: PollId, member: Member): PollModifier.CloseResult {
		return when (val closeResult: PollManager.CloseResult = this.pollManager.closePollIfAllowed(pollId, member)) {
			is PollManager.CloseResult.Denied -> PollModifier.CloseResult.Denied
			is PollManager.CloseResult.Closed -> PollModifier.CloseResult.Closed(closeResult.pollDetails)
		}
	}
}

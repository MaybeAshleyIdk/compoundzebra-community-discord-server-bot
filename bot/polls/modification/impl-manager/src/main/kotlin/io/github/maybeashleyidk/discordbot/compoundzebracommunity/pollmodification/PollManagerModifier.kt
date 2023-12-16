package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmodification

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionValue
import net.dv8tion.jda.api.entities.Member
import javax.inject.Inject

public class PollManagerModifier @Inject constructor(
	private val pollManager: PollManager,
) : PollModifier {

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

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionLabel
import net.dv8tion.jda.api.entities.Member
import javax.inject.Inject
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation.NewPollDetails as PollCreatorNewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement.NewPollDetails as PollManagerNewPollDetails

public class PollManagerCreator @Inject constructor(
	private val pollManager: PollManager,
) : PollCreator {

	override fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): PollCreatorNewPollDetails {
		return this.pollManager.openNewPoll(author, description, optionLabels)
			.toPollCreatorNewPollDetails()
	}

	override fun closePoll(pollId: PollId, closerMember: Member) {
		this.pollManager.closePollUnrestricted(pollId, closerMember)
	}
}

private fun PollManagerNewPollDetails.toPollCreatorNewPollDetails(): PollCreatorNewPollDetails {
	return PollCreatorNewPollDetails(
		id = this.id,
		authorId = this.authorId,
		description = this.description,
		options = this.options,
	)
}

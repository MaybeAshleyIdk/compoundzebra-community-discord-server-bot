package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import net.dv8tion.jda.api.entities.Member
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.NewPollDetails as PollCreatorNewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.NewPollDetails as PollManagerNewPollDetails

public class PollManagerCreator(private val pollManager: PollManager) : PollCreator {

	override suspend fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): PollCreatorNewPollDetails {
		return this.pollManager.openNewPoll(author, description, optionLabels)
			.toPollCreatorNewPollDetails()
	}

	override suspend fun closePoll(pollId: PollId, closerMember: Member) {
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

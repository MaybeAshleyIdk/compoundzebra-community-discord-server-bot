package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import net.dv8tion.jda.api.entities.Member
import javax.inject.Inject

public class PollManagerCreator @Inject constructor(
	private val pollManager: PollManager,
) : PollCreator {

	override suspend fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): NewPollDetails {
		return this.pollManager.openNewPoll(author, description, optionLabels)
	}

	override suspend fun closePoll(pollId: PollId, closerMember: Member) {
		this.pollManager.closePollUnrestricted(pollId, closerMember)
	}
}

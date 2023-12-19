package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import net.dv8tion.jda.api.entities.Member

public interface PollCreator {

	public suspend fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): NewPollDetails

	public suspend fun closePoll(pollId: PollId, closerMember: Member)
}

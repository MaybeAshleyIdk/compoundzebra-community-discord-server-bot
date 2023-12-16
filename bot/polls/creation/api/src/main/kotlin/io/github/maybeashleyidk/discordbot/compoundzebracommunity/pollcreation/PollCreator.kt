package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionLabel
import net.dv8tion.jda.api.entities.Member

public interface PollCreator {

	public suspend fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): NewPollDetails

	public suspend fun closePoll(pollId: PollId, closerMember: Member)
}

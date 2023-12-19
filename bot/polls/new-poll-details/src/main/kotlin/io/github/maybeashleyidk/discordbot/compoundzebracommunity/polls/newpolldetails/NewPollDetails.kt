package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption

public data class NewPollDetails(
	val id: PollId,
	val authorId: Long,
	val description: PollDescription,
	val options: List<PollOption>,
) {

	public fun toEmptyPollDetails(): PollDetails {
		return PollDetails(
			authorId = this.authorId,
			description = this.description,
			options = this.options,
			votes = emptyMap(),
			closerMemberId = null,
		)
	}
}

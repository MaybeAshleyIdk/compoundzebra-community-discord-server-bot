package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption

public data class NewPollDetails(
	val id: PollId,
	val authorId: Long,
	val description: PollDescription,
	val options: List<PollOption>,
)

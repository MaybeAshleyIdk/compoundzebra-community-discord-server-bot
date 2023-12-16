package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOption

public data class NewPollDetails(
	val id: PollId,
	val authorId: Long,
	val description: PollDescription,
	val options: List<PollOption>,
)

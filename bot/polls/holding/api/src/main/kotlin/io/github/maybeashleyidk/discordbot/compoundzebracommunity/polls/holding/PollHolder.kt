package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId

public interface PollHolder {

	public suspend fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails?
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollholding

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId

public interface PollHolder {

	public fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails?
}

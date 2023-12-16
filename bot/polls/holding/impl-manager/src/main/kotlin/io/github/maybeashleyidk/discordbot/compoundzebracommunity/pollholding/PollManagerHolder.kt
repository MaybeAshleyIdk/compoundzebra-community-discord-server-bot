package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollholding

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement.PollManager
import javax.inject.Inject

public class PollManagerHolder @Inject constructor(
	private val pollManager: PollManager,
) : PollHolder {

	override suspend fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails? {
		return this.pollManager.getPollDetailsByIdOrNull(pollId)
	}
}

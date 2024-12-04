package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager

public class PollManagerHolder(private val pollManager: PollManager) : PollHolder {

	override suspend fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails? {
		return this.pollManager.getPollDetailsByIdOrNull(pollId)
	}
}

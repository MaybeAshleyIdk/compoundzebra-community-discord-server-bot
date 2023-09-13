package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls

import javax.annotation.CheckReturnValue

public interface PollHolder {

	@CheckReturnValue
	public fun getPollById(pollId: PollId): PollDetails?
}

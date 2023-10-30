package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

public interface PollHolder {

	public fun getPollById(pollId: PollId): PollDetails?
}

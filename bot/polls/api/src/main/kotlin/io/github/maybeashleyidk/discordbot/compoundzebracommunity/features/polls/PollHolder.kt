package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls

public interface PollHolder {

	public fun getPollById(pollId: PollId): PollDetails?
}

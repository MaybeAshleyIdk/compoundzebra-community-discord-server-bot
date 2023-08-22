package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollId
import javax.annotation.CheckReturnValue

internal interface PollHolder {

	@CheckReturnValue
	fun getPollById(pollId: PollId): PollDetails?
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.sending

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel

public interface PollSender {

	public suspend fun createAndSendPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
		targetChannel: GuildMessageChannel,
	)
}

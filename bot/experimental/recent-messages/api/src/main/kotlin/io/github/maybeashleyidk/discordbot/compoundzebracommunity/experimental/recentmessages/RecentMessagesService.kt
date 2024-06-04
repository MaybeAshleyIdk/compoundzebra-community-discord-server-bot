package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public interface RecentMessagesService {

	// TODO: change return type to just return the message IDs?
	public fun getRecentMessagesOf(channel: MessageChannel): Sequence<Message>
}

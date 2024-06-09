package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

// TODO: needs a better name
public sealed interface MarkdownThing {

	public fun toRawString(): String

	/**
	 * Returns a string how this markdown thing would roughly look in the official Discord desktop client.
	 */
	public suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String
}

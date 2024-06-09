package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

// <https://discord.com/developers/docs/reference#message-formatting-formats>

public sealed interface TextElement : MarkdownThing

public data class ChannelLink(
	public val guildId: ULong,
	public val channelId: ULong,
) : TextElement {

	override fun toRawString(): String {
		return "https://discord.com/channels/${this.guildId}/${this.channelId}"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		TODO("Not yet implemented")
	}
}

public data class MessageLink(
	public val guildId: ULong,
	public val channelId: ULong,
	public val messageId: ULong,
) : TextElement {

	override fun toRawString(): String {
		return "https://discord.com/channels/${this.guildId}/${this.channelId}/${this.messageId}"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		TODO("Not yet implemented")
	}
}

@JvmInline
public value class Text(private val elements: List<TextElement>) :
	MarkdownElement,
	UnderlineElement,
	SpoilerElement,
	TopLevelBlockQuoteElement {

	override fun toRawString(): String {
		return this.elements
			.joinToString(
				separator = "",
				transform = TextElement::toRawString,
			)
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		TODO("Not yet implemented")
	}
}

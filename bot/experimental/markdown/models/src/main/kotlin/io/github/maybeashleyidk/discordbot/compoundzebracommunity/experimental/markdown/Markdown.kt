package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public sealed interface MarkdownElement : MarkdownThing

@JvmInline
public value class Markdown(public val elements: List<MarkdownElement>) : MarkdownThing {

	override fun toRawString(): String {
		return this.elements
			.joinToString(
				separator = "",
				transform = MarkdownElement::toRawString,
			)
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		return this.elements
			.map { element: MarkdownElement ->
				element.toDisplayString(channelContext, showSpoiler)
			}
			.joinToString(separator = "")
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import java.net.URI

public sealed interface LinkElement : MarkdownThing

public data class Link(
	public val elements: List<LinkElement>,
	public val address: URI,
	public val embedSuppressed: Boolean,
	public val hoverText: String?,
) : MarkdownElement, MarkdownThing {

	init {
		require(this.address.scheme in setOf("http", "https"))
		require((this.hoverText == null) || this.hoverText.isNotEmpty())
	}

	override fun toRawString(): String {
		val sb = StringBuilder()

		sb.append('[')
		this.elements.joinTo(sb, separator = "", transform = LinkElement::toRawString)
		sb.append("](")

		if (this.embedSuppressed) {
			sb.append('<')
		}
		sb.append(this.address.toString())
		if (this.embedSuppressed) {
			sb.append('>')
		}

		if (this.hoverText != null) {
			sb.append(" \"")
			sb.append(this.hoverText)
			sb.append('"')
		}

		sb.append(')')

		return sb.toString()
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		return this.elements
			.map { element: LinkElement ->
				element.toDisplayString(channelContext, showSpoiler)
			}
			.joinToString(separator = "")
	}
}

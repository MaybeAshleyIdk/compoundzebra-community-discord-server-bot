package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public data class Preformatted(
	public val language: String?,
	public val string: String
) : MarkdownElement, MarkdownThing {

	init {
		require((this.language == null) || this.language.isNotEmpty())
		require("```" !in this.string)
	}

	override fun toRawString(): String {
		val sb = StringBuilder()

		sb.append("```")
		if (this.language != null) {
			sb.append(this.language)
		}
		sb.append('\n')
		sb.append(this.string)
		sb.append("\n```")

		return sb.toString()
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		return this.string
	}
}

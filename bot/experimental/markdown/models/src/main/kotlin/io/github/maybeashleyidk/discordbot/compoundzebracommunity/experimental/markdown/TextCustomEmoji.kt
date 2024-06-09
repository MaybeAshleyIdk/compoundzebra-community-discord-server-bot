package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public data class CustomEmoji(
	public val animated: Boolean,
	public val name: String,
	public val id: ULong,
) : TextElement {

	init {
		require(this.name.isNotEmpty())
	}

	override fun toRawString(): String {
		val sb = StringBuilder()

		sb.append('<')
		if (this.animated) {
			sb.append('a')
		}
		sb.append(':')
		sb.append(this.name)
		sb.append(':')
		sb.append(this.id)
		sb.append('>')

		return sb.toString()
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		return ":${this.name}:"
	}
}

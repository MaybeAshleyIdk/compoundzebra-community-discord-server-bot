package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public enum class GuildNavigation(private val string: String) : TextElement {
	CUSTOMIZE("customize"),
	BROWSE_CHANNELS("browse"),
	GUILD_GUIDE("guide"),
	;

	override fun toRawString(): String {
		return "<id:${this.string}>"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		return when (this) {
			CUSTOMIZE -> "Channels & Roles"
			BROWSE_CHANNELS -> "Browse Channels"
			GUILD_GUIDE -> "Server Guide"
		}
	}
}

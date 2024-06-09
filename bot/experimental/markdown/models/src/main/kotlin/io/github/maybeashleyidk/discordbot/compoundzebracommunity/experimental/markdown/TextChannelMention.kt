package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

@JvmInline
public value class ChannelMention(public val channelId: ULong) : TextElement {

	override fun toRawString(): String {
		return "<#${this.channelId}>"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		if (channelContext.idLong.toULong() == this.channelId) {
			return "<#${channelContext.name}>"
		}

		// TODO: get all guild members that have read access to the channel <channelContext>
		//       check if all those guild members also have read access to the channel with ID <this.channelId>
		//       if yes, return the name of the channel. otherwise return the unknown channel string

		TODO("Not yet implemented")
	}
}

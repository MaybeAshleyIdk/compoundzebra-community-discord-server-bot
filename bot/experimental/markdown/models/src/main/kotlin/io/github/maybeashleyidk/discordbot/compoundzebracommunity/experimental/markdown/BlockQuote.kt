package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public sealed interface TopLevelBlockQuoteElement {

}

public interface BlockQuoteListItem

public interface BlockQuoteList : TopLevelBlockQuoteElement {

	public val singleItemBullet: ListItemBullet
}

@JvmInline
public value class TopLevelBlockQuote(
	public val elements: List<TopLevelBlockQuoteElement>,
) : MarkdownElement, MarkdownThing {

	override fun toRawString(): String {
		TODO("Not yet implemented")
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		TODO("Not yet implemented")
	}
}

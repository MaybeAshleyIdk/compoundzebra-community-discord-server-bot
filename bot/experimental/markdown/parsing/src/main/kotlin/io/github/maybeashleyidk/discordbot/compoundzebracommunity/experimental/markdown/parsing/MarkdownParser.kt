package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.parsing

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.Markdown
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.parsing.internal.ParseIterator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.parsing.internal.parseStart

public object MarkdownParser {

	public fun parseString(string: String): Markdown {
		require(string.isNotEmpty())
		return parseStart(ParseIterator(string))
	}
}

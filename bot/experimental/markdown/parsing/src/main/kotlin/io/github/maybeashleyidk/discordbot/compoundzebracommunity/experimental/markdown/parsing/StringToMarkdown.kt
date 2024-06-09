package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.parsing

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.Markdown

public fun String.toMarkdown(): Markdown {
	return MarkdownParser.parseString(this)
}

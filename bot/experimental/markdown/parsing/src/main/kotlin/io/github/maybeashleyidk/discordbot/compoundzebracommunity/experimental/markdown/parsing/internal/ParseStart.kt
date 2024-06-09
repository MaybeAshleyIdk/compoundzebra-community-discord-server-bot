package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.parsing.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.Markdown
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown.MarkdownElement

internal fun parseStart(iterator: ParseIterator): Markdown {
	val elements: MutableList<MarkdownElement> = ArrayList()

	while (iterator.hasNext()) {
		val ch: Char = iterator.next()

		when (ch) {
			'<' -> parseSpecial(iterator)
		}
	}

	return Markdown(elements)
}

private fun parseSpecial(iterator: Iterator<Char>) {
	iterator.
}

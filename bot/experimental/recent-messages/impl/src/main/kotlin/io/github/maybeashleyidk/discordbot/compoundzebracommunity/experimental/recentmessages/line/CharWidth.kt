package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.line

private val CHARS_NARROW: Set<Char> = setOf(',', ';', ':', '!', '.', '|', '1', 'i', 'j', 'l', 't')
private val CHARS_WIDE: Set<Char> = setOf('M', 'W', '\u2014', '\u2588') // U+2014 = em dash, U+2588 = full block

internal fun Char.isNarrow(): Boolean {
	return (this in CHARS_NARROW)
}

internal fun Char.isWide(): Boolean {
	return (this in CHARS_WIDE)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings

private val WHITESPACE_REGEX_PATTERN: Regex = Regex("\\s+")

public fun String.trimAndSqueezeWhitespace(): String {
	return this.trim()
		.replace(WHITESPACE_REGEX_PATTERN, replacement = " ")
}

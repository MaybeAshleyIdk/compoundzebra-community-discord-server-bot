package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils

import javax.annotation.CheckReturnValue

private val WHITESPACE_REGEX_PATTERN: Regex = Regex("\\s+")

@CheckReturnValue
public fun String.trimAndSqueezeWhitespace(): String {
	return this.trim()
		.replace(WHITESPACE_REGEX_PATTERN, replacement = " ")
}

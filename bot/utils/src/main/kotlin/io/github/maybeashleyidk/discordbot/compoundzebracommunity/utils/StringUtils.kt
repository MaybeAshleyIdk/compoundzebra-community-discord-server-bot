package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils

private val WHITESPACE_REGEX_PATTERN: Regex = Regex("\\s+")

public fun String.trimAndSqueezeWhitespace(): String {
	return this.trim()
		.replace(WHITESPACE_REGEX_PATTERN, replacement = " ")
}

public fun String.quoted(): String {
	return buildString(this.length + 2) {
		this@buildString.append('"')

		this@quoted.forEach { ch: Char ->
			if (ch == '\\' || ch == '"') {
				this@buildString.append('\\')
			}

			this@buildString.append(ch)
		}

		this@buildString.append('"')
	}
}

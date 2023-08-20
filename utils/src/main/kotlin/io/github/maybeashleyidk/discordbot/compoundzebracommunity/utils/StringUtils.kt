package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils

import javax.annotation.CheckReturnValue

private val WHITESPACE_REGEX_PATTERN: Regex = Regex("\\s+")

@CheckReturnValue
public fun String.trimAndSqueezeWhitespace(): String {
	return this.trim()
		.replace(WHITESPACE_REGEX_PATTERN, replacement = " ")
}

@CheckReturnValue
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

@CheckReturnValue
public inline fun String.indexOfFirst(startIndex: Int, predicate: (Char) -> Boolean): Int {
	var i: Int = startIndex
	while (i < this.length) {
		if (predicate(this[i])) {
			return i
		}

		++i
	}

	return -1
}

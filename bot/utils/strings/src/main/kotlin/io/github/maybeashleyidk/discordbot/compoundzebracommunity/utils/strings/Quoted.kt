package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings

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

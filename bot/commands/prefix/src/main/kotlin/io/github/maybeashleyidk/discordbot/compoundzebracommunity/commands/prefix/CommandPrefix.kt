package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.prefix

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.quoted

private const val VALID_COMMAND_PREFIX_CHARS: String = "!#%&()+,-.:;=>?[]^{|}~"

@JvmInline
public value class CommandPrefix private constructor(private val prefixString: String) {

	init {
		require(isValidCommandPrefix(this.prefixString)) {
			"Invalid command prefix string ${this.prefixString.quoted()}"
		}
	}

	override fun toString(): String {
		return this.prefixString
	}

	public companion object {

		public fun ofString(prefixString: String): CommandPrefix? {
			if (!(isValidCommandPrefix(prefixString))) {
				return null
			}

			return CommandPrefix(prefixString)
		}
	}
}

private fun isValidCommandPrefix(prefixString: String): Boolean {
	return prefixString.isNotEmpty() && prefixString.all(::isValidCommandPrefixChar)
}

private fun isValidCommandPrefixChar(ch: Char): Boolean {
	return VALID_COMMAND_PREFIX_CHARS.any(ch::equals)
}

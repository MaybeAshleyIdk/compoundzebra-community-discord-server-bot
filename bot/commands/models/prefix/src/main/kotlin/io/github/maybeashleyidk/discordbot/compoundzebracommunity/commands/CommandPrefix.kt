package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted
import javax.annotation.CheckReturnValue

@JvmInline
public value class CommandPrefix private constructor(public val string: String) {

	public companion object {

		private const val VALID_PREFIX_CHARS: String = "!#%&()+,-.:;=>?[]^{|}~"

		@CheckReturnValue
		private fun isValidCommandPrefixChar(ch: Char): Boolean {
			return this.VALID_PREFIX_CHARS
				.any { validPrefixChar: Char ->
					validPrefixChar == ch
				}
		}

		@CheckReturnValue
		public fun isValidCommandPrefix(prefixString: String): Boolean {
			return prefixString.isNotEmpty() && prefixString.all(this::isValidCommandPrefixChar)
		}

		@CheckReturnValue
		public fun ofString(prefixString: String): CommandPrefix {
			require(this.isValidCommandPrefix(prefixString)) {
				"Invalid prefix string ${prefixString.quoted()}"
			}

			return CommandPrefix(prefixString)
		}
	}

	init {
		require(this.string.isNotBlank()) {
			"Prefix string must not be blank"
		}
	}

	@CheckReturnValue
	override fun toString(): String {
		return this.string
	}
}

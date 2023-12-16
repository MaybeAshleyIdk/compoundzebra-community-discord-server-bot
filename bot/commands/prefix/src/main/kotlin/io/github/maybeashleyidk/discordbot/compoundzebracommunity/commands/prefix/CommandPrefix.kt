package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.prefix

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted

@JvmInline
public value class CommandPrefix private constructor(private val string: String) {

	public companion object {

		private const val VALID_PREFIX_CHARS: String = "!#%&()+,-.:;=>?[]^{|}~"

		public fun isValid(prefixString: String): Boolean {
			return prefixString.isNotEmpty() && prefixString.all(this::isValidCommandPrefixChar)
		}

		public fun ofString(prefixString: String): CommandPrefix {
			require(this.isValid(prefixString)) {
				"Invalid command prefix string ${prefixString.quoted()}"
			}

			return CommandPrefix(prefixString)
		}

		private fun isValidCommandPrefixChar(ch: Char): Boolean {
			return this.VALID_PREFIX_CHARS
				.any { validPrefixChar: Char ->
					validPrefixChar == ch
				}
		}
	}

	init {
		@Suppress("RemoveRedundantQualifierName")
		require(CommandPrefix.isValid(this.string)) {
			"Invalid command prefix string ${this.string.quoted()}"
		}
	}

	override fun toString(): String {
		return this.string
	}
}

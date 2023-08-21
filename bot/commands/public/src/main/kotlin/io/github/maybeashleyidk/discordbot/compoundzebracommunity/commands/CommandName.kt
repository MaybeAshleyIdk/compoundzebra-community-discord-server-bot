package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted
import javax.annotation.CheckReturnValue

@JvmInline
public value class CommandName private constructor(public val string: String) {

	public companion object {

		@CheckReturnValue
		private fun isValidCommandNameChar(ch: Char): Boolean {
			return (ch in 'a'..'z') || (ch in 'A'..'Z') || (ch in '0'..'9')
		}

		@CheckReturnValue
		public fun isValidCommandName(nameString: String): Boolean {
			return nameString.isNotEmpty() && nameString.all(this::isValidCommandNameChar)
		}

		@CheckReturnValue
		public fun ofString(nameString: String): CommandName {
			require(this.isValidCommandName(nameString)) {
				"Invalid command name ${nameString.quoted()}"
			}

			return CommandName(nameString)
		}

		@CheckReturnValue
		public fun ofStringOrNull(nameString: String): CommandName? {
			if (!(this.isValidCommandName(nameString))) {
				return null
			}

			return this.ofString(nameString)
		}
	}

	init {
		@Suppress("RemoveRedundantQualifierName")
		require(CommandName.isValidCommandName(this.string)) {
			"Invalid command name ${this.string.quoted()}"
		}
	}

	@CheckReturnValue
	public fun toQuotedString(): String {
		return buildString(1 + this.string.length + 1) {
			this@buildString.append('\'')
			this@buildString.append(this@CommandName.string)
			this@buildString.append('\'')
		}
	}

	@CheckReturnValue
	override fun toString(): String {
		return this.string
	}
}

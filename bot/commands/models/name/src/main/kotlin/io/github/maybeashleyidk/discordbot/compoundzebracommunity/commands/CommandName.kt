package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted

@JvmInline
public value class CommandName private constructor(public val string: String) {

	public companion object {

		private fun isValidCommandNameChar(ch: Char): Boolean {
			return (ch in 'a'..'z') || (ch in 'A'..'Z') || (ch in '0'..'9')
		}

		public fun isValidCommandName(nameString: String): Boolean {
			return nameString.isNotEmpty() && nameString.all(this::isValidCommandNameChar)
		}

		public fun ofString(nameString: String): CommandName {
			require(this.isValidCommandName(nameString)) {
				"Invalid command name ${nameString.quoted()}"
			}

			return CommandName(nameString)
		}
	}

	init {
		@Suppress("RemoveRedundantQualifierName")
		require(CommandName.isValidCommandName(this.string)) {
			"Invalid command name ${this.string.quoted()}"
		}
	}

	public fun toQuotedString(): String {
		return buildString(1 + this.string.length + 1) {
			this@buildString.append('\'')
			this@buildString.append(this@CommandName.string)
			this@buildString.append('\'')
		}
	}

	public fun isEquivalentTo(other: CommandName): Boolean {
		return this.string.equals(other.string, ignoreCase = true)
	}

	override fun toString(): String {
		return this.string
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandname

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted

@JvmInline
public value class CommandName private constructor(private val string: String) {

	public companion object {

		private val NAME_PATTERN: Regex = Regex(pattern = "^[a-zA-Z0-9][a-zA-Z0-9+_-]*$")

		public fun isValid(nameString: String): Boolean {
			return this.NAME_PATTERN.matches(nameString)
		}

		public fun ofString(nameString: String): CommandName {
			require(this.isValid(nameString)) {
				"Invalid command name ${nameString.quoted()}"
			}

			return CommandName(nameString)
		}
	}

	init {
		@Suppress("RemoveRedundantQualifierName")
		require(CommandName.isValid(this.string)) {
			"Invalid command name ${this.string.quoted()}"
		}
	}

	public fun toQuotedString(): String {
		return this.string.quoted()
	}

	public fun isEquivalentTo(other: CommandName): Boolean {
		return this.string.equals(other.string, ignoreCase = true)
	}

	override fun toString(): String {
		return this.string
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.quoted

private val VALID_COMMAND_NAME_PATTERN: Regex = Regex(pattern = "^[a-zA-Z0-9][a-zA-Z0-9+_-]*$")

@JvmInline
public value class CommandName public constructor(private val nameString: String) {

	init {
		require(isValidCommandName(this.nameString)) {
			"Invalid command name ${this.nameString.quoted()}"
		}
	}

	public fun toQuotedString(): String {
		return this.nameString.quoted()
	}

	public fun isEquivalentTo(other: CommandName): Boolean {
		return this.nameString.equals(other.nameString, ignoreCase = true)
	}

	override fun toString(): String {
		return this.nameString
	}

	public companion object {

		public fun ofString(nameString: String): CommandName? {
			if (!(isValidCommandName(nameString))) {
				return null
			}

			return CommandName(nameString)
		}
	}
}

private fun isValidCommandName(nameString: String): Boolean {
	return VALID_COMMAND_NAME_PATTERN.matches(nameString)
}

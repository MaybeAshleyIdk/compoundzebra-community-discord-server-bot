package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import javax.annotation.CheckReturnValue

@JvmInline
value class CommandName private constructor(val string: String) {

	companion object {

		@CheckReturnValue
		fun ofString(nameString: String): CommandName {
			require(nameString.isNotBlank()) {
				"Command name string must not be blank"
			}

			return CommandName(nameString)
		}

		@CheckReturnValue
		fun ofStringOrNull(nameString: String): CommandName? {
			if (nameString.isBlank()) {
				return null
			}

			return this.ofString(nameString)
		}
	}

	init {
		require(this.string.isNotBlank()) {
			"Command name string must not be blank"
		}
	}

	@CheckReturnValue
	fun toQuotedString(): String {
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

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import javax.annotation.CheckReturnValue

@JvmInline
public value class CommandPrefix private constructor(public val string: String) {

	public companion object {

		@CheckReturnValue
		public fun ofString(prefixString: String): CommandPrefix {
			require(prefixString.isNotBlank()) {
				"Prefix string must not be blank"
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

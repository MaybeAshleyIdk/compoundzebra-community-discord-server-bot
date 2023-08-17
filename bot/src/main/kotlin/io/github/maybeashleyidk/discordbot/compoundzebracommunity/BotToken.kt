package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import javax.annotation.CheckReturnValue

@JvmInline
value class BotToken private constructor(val string: String) {

	companion object {

		const val TOKEN_STRING_LENGTH: Int = 72

		@CheckReturnValue
		fun ofString(tokenString: String): BotToken {
			require(tokenString.length == this.TOKEN_STRING_LENGTH) {
				"Bot token string must be exactly 72 characters long"
			}

			return BotToken(tokenString)
		}
	}

	init {
		@Suppress("RemoveRedundantQualifierName")
		require(this.string.length == BotToken.TOKEN_STRING_LENGTH) {
			"Bot token string must be exactly 72 characters long"
		}
	}

	@CheckReturnValue
	override fun toString(): String {
		return buildString(this.string.length) {
			this@buildString.append("*".repeat(this@BotToken.string.length - 8))
			this@buildString.append(this@BotToken.string.takeLast(8))
		}
	}
}

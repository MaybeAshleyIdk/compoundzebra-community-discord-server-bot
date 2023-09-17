package io.github.maybeashleyidk.discordbot.compoundzebracommunity

@JvmInline
public value class BotToken private constructor(public val string: String) {

	public companion object {

		public const val TOKEN_STRING_LENGTH: Int = 72

		public fun ofString(tokenString: String): BotToken {
			require(tokenString.length == this.TOKEN_STRING_LENGTH) {
				"Bot token string must be exactly ${this.TOKEN_STRING_LENGTH} characters long"
			}

			return BotToken(tokenString)
		}
	}

	init {
		@Suppress("RemoveRedundantQualifierName")
		require(this.string.length == BotToken.TOKEN_STRING_LENGTH) {
			"Bot token string must be exactly ${BotToken.TOKEN_STRING_LENGTH} characters long"
		}
	}

	override fun toString(): String {
		return buildString(this.string.length) {
			this@buildString.append("*".repeat(this@BotToken.string.length - 8))
			this@buildString.append(this@BotToken.string.takeLast(8))
		}
	}
}

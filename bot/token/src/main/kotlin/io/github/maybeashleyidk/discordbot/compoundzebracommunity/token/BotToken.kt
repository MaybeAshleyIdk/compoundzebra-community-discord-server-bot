package io.github.maybeashleyidk.discordbot.compoundzebracommunity.token

private const val BOT_TOKEN_STRING_LENGTH: Int = 72
private const val BOT_TOKEN_REDACTED_FORM_UNREDACTED_CHARACTERS_COUNT: Int = 8

@JvmInline
public value class BotToken private constructor(private val tokenString: String) {

	init {
		require(this.tokenString.length == BOT_TOKEN_STRING_LENGTH) {
			"A Discord bot token string must be exactly $BOT_TOKEN_STRING_LENGTH characters long"
		}
	}

	public fun toRawString(): String {
		return this.tokenString
	}

	override fun toString(): String {
		val redactedCharsCount: Int = this.tokenString.length - BOT_TOKEN_REDACTED_FORM_UNREDACTED_CHARACTERS_COUNT

		val sb = StringBuilder(this.tokenString.length)

		sb.append("*".repeat(redactedCharsCount))
		sb.append(this.tokenString.takeLast(BOT_TOKEN_REDACTED_FORM_UNREDACTED_CHARACTERS_COUNT))

		return sb.toString()
	}

	public companion object {

		public const val TOKEN_STRING_LENGTH: Int = BOT_TOKEN_STRING_LENGTH

		/**
		 * Returns `null` if [tokenString] is *not* exactly [TOKEN_STRING_LENGTH] characters long.
		 */
		public fun ofString(tokenString: String): BotToken? {
			if (tokenString.length != BOT_TOKEN_STRING_LENGTH) {
				return null
			}

			return BotToken(tokenString)
		}
	}
}

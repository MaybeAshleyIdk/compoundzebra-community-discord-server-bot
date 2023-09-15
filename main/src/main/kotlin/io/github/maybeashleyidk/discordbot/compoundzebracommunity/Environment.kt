package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import javax.annotation.CheckReturnValue

sealed class EnvironmentBotToken {
	// TODO: change these into `data object`s once Moshi supports Kotlin 1.9.0
	object UnsetOrEmpty : EnvironmentBotToken()

	object InvalidLength : EnvironmentBotToken()

	data class Valid(val token: BotToken) : EnvironmentBotToken()
}

object Environment {

	const val BOT_TOKEN_VARIABLE_NAME: String = "DISCORD_BOT_TOKEN"

	@CheckReturnValue
	fun extractBotToken(): EnvironmentBotToken {
		val str: String = System.getenv(this.BOT_TOKEN_VARIABLE_NAME).orEmpty()

		if (str.isEmpty()) {
			return EnvironmentBotToken.UnsetOrEmpty
		}

		if (str.length != BotToken.TOKEN_STRING_LENGTH) {
			return EnvironmentBotToken.InvalidLength
		}

		val token: BotToken = BotToken.ofString(str)
		return EnvironmentBotToken.Valid(token)
	}
}

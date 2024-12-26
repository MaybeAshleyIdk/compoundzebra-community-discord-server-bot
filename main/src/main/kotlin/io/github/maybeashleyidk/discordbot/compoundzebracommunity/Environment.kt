package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken

internal sealed class EnvironmentBotToken {
	data object UnsetOrEmpty : EnvironmentBotToken()

	data object InvalidLength : EnvironmentBotToken()

	data class Valid(val token: BotToken) : EnvironmentBotToken()
}

internal object Environment {

	const val VARIABLE_NAME_BOT_TOKEN: String = "DISCORD_BOT_TOKEN"

	fun extractBotToken(): EnvironmentBotToken {
		val str: String = System.getenv(this.VARIABLE_NAME_BOT_TOKEN).orEmpty()

		if (str.isEmpty()) {
			return EnvironmentBotToken.UnsetOrEmpty
		}

		val token: BotToken = BotToken.ofString(str)
			?: return EnvironmentBotToken.InvalidLength

		return EnvironmentBotToken.Valid(token)
	}
}

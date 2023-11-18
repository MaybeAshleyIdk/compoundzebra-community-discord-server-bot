package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken

internal sealed class EnvironmentBotEnvironmentType {
	data object UnsetOrEmpty : EnvironmentBotEnvironmentType()

	data object Invalid : EnvironmentBotEnvironmentType()

	data class Valid(val environmentType: BotEnvironmentType) : EnvironmentBotEnvironmentType()
}

internal sealed class EnvironmentBotToken {
	data object UnsetOrEmpty : EnvironmentBotToken()

	data object InvalidLength : EnvironmentBotToken()

	data class Valid(val token: BotToken) : EnvironmentBotToken()
}

internal object Environment {

	const val VARIABLE_NAME_BOT_ENVIRONMENT_TYPE: String = "CZD_BOT_ENVIRONMENT"
	const val VARIABLE_NAME_BOT_TOKEN: String = "DISCORD_BOT_TOKEN"

	fun extractBotEnvironmentType(): EnvironmentBotEnvironmentType {
		val str: String = System.getenv(this.VARIABLE_NAME_BOT_ENVIRONMENT_TYPE).orEmpty()

		if (str.isEmpty()) {
			return EnvironmentBotEnvironmentType.UnsetOrEmpty
		}

		val environmentType: BotEnvironmentType =
			when (str) {
				"dev" -> BotEnvironmentType.DEVELOPMENT
				"prod" -> BotEnvironmentType.PRODUCTION
				else -> {
					return EnvironmentBotEnvironmentType.Invalid
				}
			}

		return EnvironmentBotEnvironmentType.Valid(environmentType)
	}

	fun extractBotToken(): EnvironmentBotToken {
		val str: String = System.getenv(this.VARIABLE_NAME_BOT_TOKEN).orEmpty()

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

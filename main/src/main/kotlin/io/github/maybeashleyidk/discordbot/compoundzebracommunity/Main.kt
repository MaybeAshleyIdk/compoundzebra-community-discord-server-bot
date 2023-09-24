@file:JvmName("Main")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import java.nio.file.Path
import kotlin.system.exitProcess

private val CONFIG_FILE_PATH: Path = Path.of("bot_config.json")

fun main() {
	val environmentType: BotEnvironmentType = getBotEnvironmentTypeFromEnvironmentOrExit()
	val token: BotToken = getBotTokenFromEnvironmentOrExit()
	Bot.run(
		environmentType = environmentType,
		token = token,
		configFilePath = CONFIG_FILE_PATH, // TODO: add option parsing and allow to change it via option -c, --config
	)
}

private fun getBotEnvironmentTypeFromEnvironmentOrExit(): BotEnvironmentType {
	val environmentBotEnvironmentType: EnvironmentBotEnvironmentType = Environment.extractBotEnvironmentType()
	return when (environmentBotEnvironmentType) {
		is EnvironmentBotEnvironmentType.UnsetOrEmpty -> {
			val msg: String = "environment variable ${Environment.VARIABLE_NAME_BOT_ENVIRONMENT_TYPE}" +
				" must not be unset or empty"
			System.err.println(msg)

			exitProcess(48)
		}

		is EnvironmentBotEnvironmentType.Invalid -> {
			val msg: String = "value of environment variable ${Environment.VARIABLE_NAME_BOT_ENVIRONMENT_TYPE}" +
				" must be either \"dev\" or \"prod\""
			System.err.println(msg)

			exitProcess(49)
		}

		is EnvironmentBotEnvironmentType.Valid -> environmentBotEnvironmentType.environmentType
	}
}

private fun getBotTokenFromEnvironmentOrExit(): BotToken {
	return when (val environmentBotToken: EnvironmentBotToken = Environment.extractBotToken()) {
		is EnvironmentBotToken.UnsetOrEmpty -> {
			System.err.println("environment variable ${Environment.VARIABLE_NAME_BOT_TOKEN} must not be unset or empty")
			exitProcess(50)
		}

		is EnvironmentBotToken.InvalidLength -> {
			val msg: String = "value of environment variable ${Environment.VARIABLE_NAME_BOT_TOKEN}" +
				" must be exactly ${BotToken.TOKEN_STRING_LENGTH} characters long"
			System.err.println(msg)

			exitProcess(51)
		}

		is EnvironmentBotToken.Valid -> environmentBotToken.token
	}
}

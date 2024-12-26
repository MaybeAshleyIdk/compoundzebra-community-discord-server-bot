@file:JvmName("Main")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import java.nio.file.Path

private val CONFIG_FILE_PATH: Path = Path.of("bot_config.json")

fun main() {
	val token: BotToken = getBotTokenFromEnvironmentOrExit()
	Bot.run(
		buildType = BUILD_TYPE,
		token = token,
		configFilePath = CONFIG_FILE_PATH, // TODO: add option parsing and allow to change it via option -c, --config
	)
}

private fun getBotTokenFromEnvironmentOrExit(): BotToken {
	return when (val environmentBotToken: EnvironmentBotToken = Environment.extractBotToken()) {
		is EnvironmentBotToken.UnsetOrEmpty -> {
			System.err.println("environment variable ${Environment.VARIABLE_NAME_BOT_TOKEN} must not be unset or empty")
			exitProcess(ExitStatus.UNSET_OR_EMPTY_BOT_TOKEN_ENVIRONMENT_VARIABLE)
		}

		is EnvironmentBotToken.InvalidLength -> {
			val msg: String = "value of environment variable ${Environment.VARIABLE_NAME_BOT_TOKEN}" +
				" must be exactly ${BotToken.TOKEN_STRING_LENGTH} characters long"
			System.err.println(msg)

			exitProcess(ExitStatus.INVALID_BOT_TOKEN_LENGTH)
		}

		is EnvironmentBotToken.Valid -> environmentBotToken.token
	}
}

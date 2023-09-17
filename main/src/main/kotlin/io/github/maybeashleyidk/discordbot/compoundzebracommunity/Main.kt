@file:JvmName("Main")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import java.nio.file.Path
import kotlin.system.exitProcess

private val CONFIG_FILE_PATH: Path = Path.of("bot_config.json")

fun main() {
	val token: BotToken = getBotTokenFromEnvironmentOrExit()
	Bot.run(
		token = token,
		configFilePath = CONFIG_FILE_PATH, // TODO: add option parsing and allow to change it via option -c, --config
	)
}

private fun getBotTokenFromEnvironmentOrExit(): BotToken {
	return when (val environmentBotToken: EnvironmentBotToken = Environment.extractBotToken()) {
		is EnvironmentBotToken.UnsetOrEmpty -> {
			System.err.println("environment variable ${Environment.BOT_TOKEN_VARIABLE_NAME} must not be unset or empty")
			exitProcess(48)
		}

		is EnvironmentBotToken.InvalidLength -> {
			val msg: String = "value of environment variable ${Environment.BOT_TOKEN_VARIABLE_NAME}" +
				" must be exactly ${BotToken.TOKEN_STRING_LENGTH} characters long"
			System.err.println(msg)

			exitProcess(49)
		}

		is EnvironmentBotToken.Valid -> environmentBotToken.token
	}
}

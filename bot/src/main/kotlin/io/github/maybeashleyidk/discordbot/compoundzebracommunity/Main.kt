@file:JvmName("Main")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import javax.annotation.CheckReturnValue
import kotlin.system.exitProcess

fun main() {
	val token: BotToken = getBotTokenFromEnvironmentOrExit()
	Bot.run(token = token)
}

@CheckReturnValue
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

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.awaitShutdown
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path

public object Bot {

	public fun run(environmentType: BotEnvironmentType, token: BotToken, configFilePath: Path) {
		val botModule =
			BotModule(
				scope = DiScope(),
				environmentType,
				token,
				initialActivity = Activity.playing("you like a damn fiddle"),
				configFilePath,
			)

		botModule.logToken()

		botModule.logger.logInfo("Waiting until the bot is connected...")
		botModule.lazyJda.value.awaitReady()
		botModule.logger.logInfo("Bot connected!")

		runBlocking {
			botModule.shutdownCallbackRegistry.awaitShutdown()
		}
	}
}

private fun BotModule.logToken() {
	val msg: String =
		buildString(13 + BotToken.TOKEN_STRING_LENGTH) {
			this@buildString.append("Using token: ")
			this@buildString.append(this@logToken.token.toString())
		}

	this.logger.logInfo(msg)
}

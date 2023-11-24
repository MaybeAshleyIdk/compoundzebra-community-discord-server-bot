package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbackregistry.awaitShutdown
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path

public object Bot {

	public fun run(environmentType: BotEnvironmentType, token: BotToken, configFilePath: Path) {
		val botComponent: BotComponent = DaggerBotComponent.factory()
			.build(
				environmentType,
				token,
				initialActivity = Activity.playing("you like a damn fiddle"),
				configFilePath,
			)

		botComponent.logToken()

		botComponent.logger.logInfo("Waiting until the bot is connected...")
		botComponent.lazyJda.get().awaitReady()
		botComponent.logger.logInfo("Bot connected!")

		runBlocking {
			botComponent.shutdownCallbackRegistry.awaitShutdown()
		}
	}
}

private fun BotComponent.logToken() {
	val msg: String =
		buildString(13 + BotToken.TOKEN_STRING_LENGTH) {
			this@buildString.append("Using token: ")
			this@buildString.append(this@logToken.token.toString())
		}

	this.logger.logInfo(msg)
}

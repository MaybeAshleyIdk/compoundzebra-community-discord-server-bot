package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import java.time.Duration
import net.dv8tion.jda.api.JDA as Jda

public object Bot {

	private val GRACEFUL_SHUTDOWN_TIMEOUT_DURATION: Duration = Duration.ofSeconds(10)

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

		botComponent.shutdownAwaiter.awaitShutdownRequest()
		this.shutdownGracefully(botComponent.lazyJda.get(), botComponent.logger)
	}

	private fun shutdownGracefully(jda: Jda, logger: Logger) {
		logger.logInfo("Shutting down...")
		jda.presence.setPresence(OnlineStatus.OFFLINE, null, false)
		jda.shutdown()

		if (!(jda.awaitShutdown(this.GRACEFUL_SHUTDOWN_TIMEOUT_DURATION))) {
			this.shutdownForcefully(jda, logger)
			return
		}

		logger.logInfo("Shutdown successful")
	}

	private fun shutdownForcefully(jda: Jda, logger: Logger) {
		val logMsg: String = "Graceful shutdown did not succeed" +
			" after a duration of ${this.GRACEFUL_SHUTDOWN_TIMEOUT_DURATION}." +
			" Attempting a forceful shutdown..."
		logger.logWarning(logMsg)

		jda.shutdownNow()
		jda.awaitShutdown()

		logger.logWarning("Forceful shutdown successful")
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

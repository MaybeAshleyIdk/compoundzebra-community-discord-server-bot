package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.DaggerJdaComponent
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.JdaComponent
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.build
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.token
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import java.time.Duration
import net.dv8tion.jda.api.JDA as Jda

object Bot {

	private val GRACEFUL_SHUTDOWN_TIMEOUT_DURATION: Duration = Duration.ofSeconds(10)

	fun run(token: BotToken) {
		val jdaComponent: JdaComponent = DaggerJdaComponent.factory()
			.build(
				token,
				initialActivity = Activity.playing("you like a damn fiddle"),
			)

		jdaComponent.logToken()

		jdaComponent.logger.logInfo("Waiting until the bot is connected...")
		jdaComponent.lazyJda.get().awaitReady()
		jdaComponent.logger.logInfo("Bot connected!")

		jdaComponent.shutdownManager.waitForShutdownRequest()
		this.shutdownGracefully(jdaComponent.lazyJda.get(), jdaComponent.logger)
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

private fun JdaComponent.logToken() {
	val msg: String =
		buildString(13 + BotToken.TOKEN_STRING_LENGTH) {
			this@buildString.append("Using token: ")
			this@buildString.append(this@logToken.token.toString())
		}

	this.logger.logInfo(msg)
}

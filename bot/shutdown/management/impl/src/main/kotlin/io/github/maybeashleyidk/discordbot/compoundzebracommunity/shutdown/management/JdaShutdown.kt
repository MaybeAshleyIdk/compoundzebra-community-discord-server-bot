package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.OnlineStatus
import java.time.Duration
import net.dv8tion.jda.api.JDA as Jda

private val GRACEFUL_SHUTDOWN_TIMEOUT_DURATION: Duration = Duration.ofSeconds(10)

/**
 * Sets the [presence][Jda.getPresence] of the [JDA][Jda] instance [jda] to [offline][OnlineStatus.OFFLINE] and
 * blocks the thread until it has fully shut down.
 */
internal fun shutDownJda(jda: Jda, logger: Logger) {
	logger.logInfo("Shutting down...")

	jda.presence.setPresence(OnlineStatus.OFFLINE, null, false)

	jda.shutdown()
	if (jda.awaitShutdown(GRACEFUL_SHUTDOWN_TIMEOUT_DURATION)) {
		logger.logInfo("Shutdown successful")
		return
	}

	val logMsg: String = "Graceful shutdown did not succeed" +
		" after a duration of ${GRACEFUL_SHUTDOWN_TIMEOUT_DURATION}." +
		" Attempting a forceful shutdown..."
	logger.logWarning(logMsg)

	jda.shutdownNow()
	jda.awaitShutdown()

	logger.logWarning("Forceful shutdown successful")
}

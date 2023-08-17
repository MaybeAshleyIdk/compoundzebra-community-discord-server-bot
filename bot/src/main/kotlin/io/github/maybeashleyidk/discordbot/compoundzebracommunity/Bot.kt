package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.DaggerJdaComponent
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.JdaComponent
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.build
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.di.token
import net.dv8tion.jda.api.entities.Activity

object Bot {

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

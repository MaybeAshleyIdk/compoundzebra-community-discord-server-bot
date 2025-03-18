package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.buildtype.BotBuildType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.awaitShutdown
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.requests.RestAction
import java.nio.file.Path
import net.dv8tion.jda.api.JDA as Jda

public object Bot {

	public fun run(buildType: BotBuildType, token: BotToken, configFilePath: Path) {
		val botModule =
			BotModule(
				scope = DiScope(),
				buildType,
				token,
				initialActivity = Activity.playing("you like a damn fiddle"),
				configFilePath,
			)

		botModule.logToken()

		botModule.logger.logInfo("Waiting until the bot is connected...")
		botModule.lazyJda.value.awaitReady()
		botModule.logger.logInfo("Bot connected!")

		runBlocking {
			val jda: Jda = botModule.lazyJda.value

			jda.deleteAllCommands()

			val commands: List<Command> = jda.updateCommands()
				.addCommands(
					Commands.slash("quotes_channel", "1")
						.addSubcommands(
							SubcommandData("set-up", "2")
								.addOption(OptionType.CHANNEL, "channel", "7", true),
							SubcommandData("info", "3"),
							SubcommandData("enable", "4"),
							SubcommandData("disable", "5"),
							SubcommandData("remove", "6"),
						),
				)
				.await()

			commands.single().id

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

private suspend fun Jda.deleteAllCommands() {
	val commands: List<Command> = this.retrieveCommands().await()

	if (commands.isEmpty()) {
		return
	}

	commands
		.map { command: Command ->
			this.deleteCommandById(command.idLong)
		}
		.let { RestAction.allOf(it) }
		.await()
}

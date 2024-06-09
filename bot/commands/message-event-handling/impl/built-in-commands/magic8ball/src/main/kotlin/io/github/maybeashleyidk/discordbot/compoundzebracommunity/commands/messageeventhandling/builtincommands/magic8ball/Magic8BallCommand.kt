package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.magic8ball

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class Magic8BallCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val logger: Logger,
) : Command(name = CommandName.ofString("8ball")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val config: Config = this.configSupplier.get()

		if (config.strings.command.magic8Ball.responses.isEmpty()) {
			this.logger.logWarning("No magic 8 ball responses configured. Ignoring this execution of the command")
			return
		}

		val response: String =
			if (arguments.any(String::isNotBlank)) {
				config.strings.command.magic8Ball.responses.random()
			} else {
				config.strings.command.magic8Ball.missingQuestion
			}

		catalystMessage.reply(response).await()
	}
}

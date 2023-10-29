package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.magic8ball

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

internal class Magic8BallCommand @Inject constructor(
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

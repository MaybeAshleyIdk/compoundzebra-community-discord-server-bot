package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.coinflip

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import kotlin.random.Random

public class CoinFlipCommand(
	private val configSupplier: ConfigSupplier,
) : Command(name = CommandName("coin")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val isHeads: Boolean = Random.nextBoolean()

		val config: Config = this.configSupplier.get()

		val resultString: String =
			if (isHeads) {
				config.strings.command.coinFlip.heads
			} else {
				config.strings.command.coinFlip.tails
			}

		catalystMessage.reply(resultString).await()
	}
}

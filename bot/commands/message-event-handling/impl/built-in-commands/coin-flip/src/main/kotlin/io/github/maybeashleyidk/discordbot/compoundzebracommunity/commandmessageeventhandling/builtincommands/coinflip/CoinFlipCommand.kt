package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.coinflip

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandname.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject
import kotlin.random.Random

public class CoinFlipCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
) : Command(name = CommandName.ofString("coin")) {

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

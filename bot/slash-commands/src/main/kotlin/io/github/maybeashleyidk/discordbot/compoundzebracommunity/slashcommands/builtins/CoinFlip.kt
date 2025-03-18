package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.builtins

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.ImmutableSlashCommandData
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.SlashCommandEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.createSlashCommandInteractionEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.SlashCommandRegistrant
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.SlashCommandsRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.register
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import kotlin.random.Random

internal object CoinFlipSlashCommand {

	val DATA: ImmutableSlashCommandData = ImmutableSlashCommandData("coin", "Flip a coin")

	fun createEventHandler(configSupplier: ConfigSupplier): SlashCommandEventHandler {
		return createSlashCommandInteractionEventHandler { event: SlashCommandInteractionEvent ->
			val isHeads: Boolean = Random.nextBoolean()

			val config: Config = configSupplier.get()

			val resultString: String =
				if (isHeads) {
					config.strings.command.coinFlip.heads
				} else {
					config.strings.command.coinFlip.tails
				}

			event.reply(resultString).await()
		}
	}
}

public class CoinFlipSlashCommandRegistrant(private val configSupplier: ConfigSupplier) : SlashCommandRegistrant {

	public override fun registerIn(registry: SlashCommandsRegistry) {
		registry.register(
			Commands.slash("coin", "Flip a coin"),
			this::executeCommand,
		)
	}

	private suspend fun executeCommand(event: SlashCommandInteractionEvent) {
		val isHeads: Boolean = Random.nextBoolean()

		val config: Config = this.configSupplier.get()

		val resultString: String =
			if (isHeads) {
				config.strings.command.coinFlip.heads
			} else {
				config.strings.command.coinFlip.tails
			}

		event.reply(resultString).await()
	}
}

internal fun SlashCommandsRegistry.registerCoinFlip(configSupplier: ConfigSupplier) {
	this.register(
		Commands.slash("coin", "Flip a coin"),
	) { event: SlashCommandInteractionEvent ->
		val isHeads: Boolean = Random.nextBoolean()

		val config: Config = configSupplier.get()

		val resultString: String =
			if (isHeads) {
				config.strings.command.coinFlip.heads
			} else {
				config.strings.command.coinFlip.tails
			}

		event.reply(resultString).await()
	}
}

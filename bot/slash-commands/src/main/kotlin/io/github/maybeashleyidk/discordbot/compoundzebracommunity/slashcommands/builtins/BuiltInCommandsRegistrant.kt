package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.builtins

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.SlashCommandsRegistry

public class BuiltInCommandsRegistrant(
	private val configSupplier: ConfigSupplier,
) {

	public fun registerIn(registry: SlashCommandsRegistry) {

		registry.registerCoinFlip(this.configSupplier)
	}
}

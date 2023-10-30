package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config

internal fun createInitialConfig(botEnvironmentType: BotEnvironmentType): Config {
	val fallbackConfig: Config = createFallbackConfig(botEnvironmentType)
	return fallbackConfig
		.copy(
			commandDefinitions = fallbackConfig.commandDefinitions +
				setOf(
					CommandDefinition(
						commandName = CommandName.ofString("ping"),
						action = Action.Response(message = "pong"),
					),
				),
		)
}

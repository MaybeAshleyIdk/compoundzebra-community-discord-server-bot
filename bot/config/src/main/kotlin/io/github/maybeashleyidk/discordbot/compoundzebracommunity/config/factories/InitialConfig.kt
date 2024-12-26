package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.buildtype.BotBuildType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config

internal fun createInitialConfig(botBuildType: BotBuildType): Config {
	val fallbackConfig: Config = createFallbackConfig(botBuildType)
	return fallbackConfig
		.copy(
			commandDefinitions = fallbackConfig.commandDefinitions +
				setOf(
					CommandDefinition(
						commandName = CommandName("ping"),
						action = Action.Response(message = "pong"),
					),
				),
		)
}

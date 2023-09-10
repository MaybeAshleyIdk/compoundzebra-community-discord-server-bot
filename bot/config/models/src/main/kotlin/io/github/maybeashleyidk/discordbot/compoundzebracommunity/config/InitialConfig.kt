package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName

internal val INITIAL_CONFIG: Config = FALLBACK_CONFIG
	.copy(
		commandDefinitions = FALLBACK_CONFIG.commandDefinitions +
			setOf(
				CommandDefinition(
					commandName = CommandName.ofString("ping"),
					action = Action.Response(message = "pong"),
				),
			),
	)

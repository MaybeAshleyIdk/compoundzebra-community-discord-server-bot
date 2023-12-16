package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName

public data class CommandDefinition(
	val commandName: CommandName,
	val action: Action,
)

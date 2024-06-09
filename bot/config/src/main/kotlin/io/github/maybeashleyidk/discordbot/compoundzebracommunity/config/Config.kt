package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.prefix.CommandPrefix

public data class Config(
	val strings: LanguageStrings,
	val botAdminUserIds: Set<String>,
	val commandPrefix: CommandPrefix,
	val commandDefinitions: Set<CommandDefinition>,
	val conditionalMessages: Set<ConditionalMessage>,
	val disabledCommandNames: Set<CommandName>,
)

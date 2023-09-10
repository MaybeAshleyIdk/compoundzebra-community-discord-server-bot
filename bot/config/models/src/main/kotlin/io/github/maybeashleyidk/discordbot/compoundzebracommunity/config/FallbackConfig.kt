package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandPrefix

internal val FALLBACK_CONFIG: Config =
	Config(
		strings = LanguageStrings(
			generic = LanguageStrings.Generic(
				invalidCommandNameFormat = "Unknown command \"%s\"",
				unknownCommandFormat = "Unknown command \"%s\"",
			),
			poll = LanguageStrings.Poll(
				titleFormat = "### Poll by %s:",
				optionFormat = "%s: %s",
				closedMessageFormat = "*Manually closed by %s*",
				action = LanguageStrings.Poll.Action(
					close = "Close",
				),
			),
			command = LanguageStrings.Command(
				poll = LanguageStrings.Command.Poll(
					missingDescription = "You must provide a description",
					lessThan2Options = "You must provide at least 2 options",
				),
				queryPoll = LanguageStrings.Command.QueryPoll(
					insufficientPermissions = "You are not allowed to use this command",
					missingId = "You must provide the poll ID (obtained from the bot logs)",
					noSuchPollWithId = "No poll with this ID",
				),
				getConfig = LanguageStrings.Command.GetConfig(
					insufficientPermissions = "You are not allowed to use this command",
				),
				shutdown = LanguageStrings.Command.Shutdown(
					response = "Shutting down. Bye bye...",
					insufficientPermissions = "You are not allowed to use this command",
				),
			),
		),
		botAdminUserIds = emptySet(),
		commandPrefix = CommandPrefix.ofString("!"),
		commandDefinitions = emptySet(),
	)

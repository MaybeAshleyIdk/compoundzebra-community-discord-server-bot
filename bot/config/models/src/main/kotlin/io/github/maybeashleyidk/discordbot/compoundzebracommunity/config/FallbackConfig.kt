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
				magic8Ball = LanguageStrings.Command.Magic8Ball(
					missingQuestion = "No question provided",
					responses = listOf(
						"It is certain",
						"It is decidedly so",
						"Without a doubt",
						"Yes definitely",
						"You may rely on it",
						"As I see it, yes",
						"Most likely",
						"Outlook good",
						"Yes",
						"Signs point to yes",
						"Reply hazy, try again",
						"Ask again later",
						"Better not tell you now",
						"Cannot predict now",
						"Concentrate and ask again",
						"Don't count on it",
						"My reply is no",
						"My sources say no",
						"Outlook not so good",
						"Very doubtful",
					),
				),
				emojiStats = LanguageStrings.Command.EmojiStats(
					loading = "Crunching the numbers\u2026 this may take a while\u2026", // U+2026 = horizontal ellipsis
					errorOccurred = "An error occurred",
					empty = "You have not used any server emojis yet",
					headingFormat = "Emoji statistics of %s:",
					statLineFormat = "%s \u2014 %s", // U+2014 = em dash
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

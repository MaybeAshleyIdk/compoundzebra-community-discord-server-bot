package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.prefix.CommandPrefix
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType

internal fun createFallbackConfig(botEnvironmentType: BotEnvironmentType): Config {
	val commandPrefix: CommandPrefix =
		when (botEnvironmentType) {
			BotEnvironmentType.DEVELOPMENT -> CommandPrefix.ofString("!!")!!
			BotEnvironmentType.PRODUCTION -> CommandPrefix.ofString("!")!!
		}

	return Config(
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
				coinFlip = LanguageStrings.Command.CoinFlip(
					heads = "heads",
					tails = "tails",
				),
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
				rng = LanguageStrings.Command.Rng(
					missingMinAndMaxArguments = "Missing minimum and maximum numbers",
					missingMaxArgument = "Missing maximum number",
					excessArguments = "Too many arguments. Expected only minimum and maximum numbers",
					minStringTooLong = "The minimum number argument is too long",
					minInvalidNumber = "The minimum number argument is not a valid number",
					minTooSmall = "The minimum number is too small",
					minTooBig = "The minimum number is too big",
					minIsDecimal = "Decimal numbers are not supported",
					maxStringTooLong = "The maximum number argument is too long",
					maxInvalidNumber = "The maximum number argument is not a valid number",
					maxTooSmall = "The maximum number is too small",
					maxTooBig = "The maximum number is too big",
					maxIsDecimal = "Decimal numbers are not supported",
					minAndMaxAreEqual = "The minimum number and the maximum number are the same",
					minGreaterThanMax = "The minimum number is greater than the maximum number",
					responseFormat = "%s",
				),
				shutdown = LanguageStrings.Command.Shutdown(
					response = "Shutting down. Bye bye...",
					insufficientPermissions = "You are not allowed to use this command",
				),
			),
		),
		botAdminUserIds = emptySet(),
		commandPrefix = commandPrefix,
		commandDefinitions = emptySet(),
		conditionalMessages = emptySet(),
		disabledCommandNames = emptySet(),
	)
}

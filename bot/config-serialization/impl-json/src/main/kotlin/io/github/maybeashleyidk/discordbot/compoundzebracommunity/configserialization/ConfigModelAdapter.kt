package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.prefix.CommandPrefix
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConditionalMessage
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings

internal object ConfigModelAdapter {

	internal fun transformConfig(configJson: ConfigJson): Config {
		return Config(
			strings = configJson.strings.toLanguageStrings(),
			botAdminUserIds = configJson.botAdminUserIds.orEmpty(),
			commandPrefix = CommandPrefix.ofString(configJson.commandPrefix),
			commandDefinitions = configJson.commands.orEmpty().mapToCommandDefinitions(),
			conditionalMessages = configJson.conditionalMessages.orEmpty().mapToConditionalMessages(),
			disabledCommandNames = configJson.disabledCommandNames.orEmpty().mapToCommandNamesIfValid(),
		)
	}

	internal fun transformConfig(config: Config): ConfigJson {
		return ConfigJson(
			strings = @Suppress("ktlint:standard:no-blank-line-in-list") LanguageStringsJson(
				genericInvalidCommandName = config.strings.generic.invalidCommandNameFormat,
				genericUnknownCommand = config.strings.generic.unknownCommandFormat,

				pollTitle = config.strings.poll.titleFormat,
				pollOption = config.strings.poll.optionFormat,
				pollClosedMessage = config.strings.poll.closedMessageFormat,
				pollActionClose = config.strings.poll.action.close,

				commandCoinHeads = config.strings.command.coinFlip.heads,
				commandCoinTails = config.strings.command.coinFlip.tails,

				commandPollMissingDescription = config.strings.command.poll.missingDescription,
				commandPollLessThan2Options = config.strings.command.poll.lessThan2Options,

				commandQuerypollInsufficientPermissions = config.strings.command.queryPoll.insufficientPermissions,
				commandQuerypollMissingId = config.strings.command.queryPoll.missingId,
				commandQuerypollNoSuchPollWithId = config.strings.command.queryPoll.noSuchPollWithId,

				command8ballMissingQuestion = config.strings.command.magic8Ball.missingQuestion,
				command8ballResponses = config.strings.command.magic8Ball.responses,

				commandEmotestatsLoading = config.strings.command.emojiStats.loading,
				commandEmotestatsErrorOccurred = config.strings.command.emojiStats.errorOccurred,
				commandEmotestatsEmpty = config.strings.command.emojiStats.empty,
				commandEmotestatsHeading = config.strings.command.emojiStats.headingFormat,
				commandEmotestatsStatLine = config.strings.command.emojiStats.statLineFormat,

				commandGetconfigInsufficientPermissions = config.strings.command.getConfig.insufficientPermissions,

				commandRngMissingMinAndMaxArguments = config.strings.command.rng.missingMinAndMaxArguments,
				commandRngMissingMaxArgument = config.strings.command.rng.missingMaxArgument,
				commandRngExcessArguments = config.strings.command.rng.excessArguments,
				commandRngMinStringTooLong = config.strings.command.rng.minStringTooLong,
				commandRngMinInvalidNumber = config.strings.command.rng.minInvalidNumber,
				commandRngMinTooSmall = config.strings.command.rng.minTooSmall,
				commandRngMinTooBig = config.strings.command.rng.minTooBig,
				commandRngMinIsDecimal = config.strings.command.rng.minIsDecimal,
				commandRngMaxStringTooLong = config.strings.command.rng.maxStringTooLong,
				commandRngMaxInvalidNumber = config.strings.command.rng.maxInvalidNumber,
				commandRngMaxTooSmall = config.strings.command.rng.maxTooSmall,
				commandRngMaxTooBig = config.strings.command.rng.maxTooBig,
				commandRngMaxIsDecimal = config.strings.command.rng.maxIsDecimal,
				commandRngMinAndMaxAreEqual = config.strings.command.rng.minAndMaxAreEqual,
				commandRngMinGreaterThanMax = config.strings.command.rng.minGreaterThanMax,
				commandRngResponse = config.strings.command.rng.responseFormat,

				commandShutdownResponse = config.strings.command.shutdown.response,
				commandShutdownInsufficientPermissions = config.strings.command.shutdown.insufficientPermissions,
			),
			botAdminUserIds = config.botAdminUserIds.ifEmpty { null },
			commandPrefix = config.commandPrefix.toString(),
			commands = config.commandDefinitions
				.associate { commandDefinition: CommandDefinition ->
					commandDefinition.commandName.toString() to commandDefinition.toCommandDetailsJson()
				}
				.ifEmpty { null },
			conditionalMessages = config.conditionalMessages.mapToConditionalMessagesJson().ifEmpty { null },
			disabledCommandNames = config.disabledCommandNames.mapToStrings().ifEmpty { null },
		)
	}
}

private fun CommandDefinition.toCommandDetailsJson(): CommandDetailsJson {
	return CommandDetailsJson(
		action = when (val action: Action = this.action) {
			is Action.Response -> ActionJson(responseMessage = action.message)
		},
	)
}

private fun LanguageStringsJson.toLanguageStrings(): LanguageStrings {
	return LanguageStrings(
		generic = LanguageStrings.Generic(
			invalidCommandNameFormat = this.genericInvalidCommandName,
			unknownCommandFormat = this.genericUnknownCommand,
		),
		poll = LanguageStrings.Poll(
			titleFormat = this.pollTitle,
			optionFormat = this.pollOption,
			closedMessageFormat = this.pollClosedMessage,
			action = LanguageStrings.Poll.Action(
				close = this.pollActionClose,
			),
		),
		command = LanguageStrings.Command(
			coinFlip = LanguageStrings.Command.CoinFlip(
				heads = this.commandCoinHeads,
				tails = this.commandCoinTails,
			),
			poll = LanguageStrings.Command.Poll(
				missingDescription = this.commandPollMissingDescription,
				lessThan2Options = this.commandPollLessThan2Options,
			),
			queryPoll = LanguageStrings.Command.QueryPoll(
				insufficientPermissions = this.commandQuerypollInsufficientPermissions,
				missingId = this.commandQuerypollMissingId,
				noSuchPollWithId = this.commandQuerypollNoSuchPollWithId,
			),
			magic8Ball = LanguageStrings.Command.Magic8Ball(
				missingQuestion = this.command8ballMissingQuestion,
				responses = this.command8ballResponses,
			),
			emojiStats = LanguageStrings.Command.EmojiStats(
				loading = this.commandEmotestatsLoading,
				errorOccurred = this.commandEmotestatsErrorOccurred,
				empty = this.commandEmotestatsEmpty,
				headingFormat = this.commandEmotestatsHeading,
				statLineFormat = this.commandEmotestatsStatLine,
			),
			getConfig = LanguageStrings.Command.GetConfig(
				insufficientPermissions = this.commandGetconfigInsufficientPermissions,
			),
			rng = LanguageStrings.Command.Rng(
				missingMinAndMaxArguments = this.commandRngMissingMinAndMaxArguments,
				missingMaxArgument = this.commandRngMissingMaxArgument,
				excessArguments = this.commandRngExcessArguments,
				minStringTooLong = this.commandRngMinStringTooLong,
				minInvalidNumber = this.commandRngMinInvalidNumber,
				minTooSmall = this.commandRngMinTooSmall,
				minTooBig = this.commandRngMinTooBig,
				minIsDecimal = this.commandRngMinIsDecimal,
				maxStringTooLong = this.commandRngMaxStringTooLong,
				maxInvalidNumber = this.commandRngMaxInvalidNumber,
				maxTooSmall = this.commandRngMaxTooSmall,
				maxTooBig = this.commandRngMaxTooBig,
				maxIsDecimal = this.commandRngMaxIsDecimal,
				minAndMaxAreEqual = this.commandRngMinAndMaxAreEqual,
				minGreaterThanMax = this.commandRngMinGreaterThanMax,
				responseFormat = this.commandRngResponse,
			),
			shutdown = LanguageStrings.Command.Shutdown(
				response = this.commandShutdownResponse,
				insufficientPermissions = this.commandShutdownInsufficientPermissions,
			),
		),
	)
}

private fun Map<String, CommandDetailsJson>.mapToCommandDefinitions(): Set<CommandDefinition> {
	return this
		.mapTo(LinkedHashSet(this.size)) { (commandNameStr: String, details: CommandDetailsJson) ->
			CommandDefinition(
				commandName = CommandName(commandNameStr),
				action = details.action.mapToAction(),
			)
		}
}

private fun Set<ConditionalMessageJson>.mapToConditionalMessages(): Set<ConditionalMessage> {
	return this
		.mapTo(LinkedHashSet(this.size)) { conditionalMessageJson: ConditionalMessageJson ->
			ConditionalMessage(
				condition = ConditionalMessage.Condition(
					regex = Regex(conditionalMessageJson.regexPattern),
				),
				messageContent = conditionalMessageJson.messageContent,
			)
		}
}

private fun Set<ConditionalMessage>.mapToConditionalMessagesJson(): Set<ConditionalMessageJson> {
	return this
		.mapTo(LinkedHashSet(this.size)) { conditionalMessage: ConditionalMessage ->
			ConditionalMessageJson(
				regexPattern = conditionalMessage.condition.regex.pattern,
				messageContent = conditionalMessage.messageContent,
			)
		}
}

private fun Set<CommandName>.mapToStrings(): Set<String> {
	return this
		.mapTo(
			LinkedHashSet(this.size),
			CommandName::toString,
		)
}

private fun Set<String>.mapToCommandNamesIfValid(): Set<CommandName> {
	return this.mapNotNullTo(LinkedHashSet(this.size), CommandName::ofString)
}

private fun ActionJson.mapToAction(): Action {
	return Action.Response(
		message = this.responseMessage,
	)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandPrefix
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings
import java.time.LocalTime
import javax.inject.Inject
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ScheduledMessage as LocalScheduledMessage

internal class ConfigModelAdapter @Suppress("ktlint:standard:annotation") @Inject constructor() {

	fun transformConfig(configJson: ConfigJson): Config {
		return Config(
			strings = configJson.strings.toLanguageStrings(),
			botAdminUserIds = configJson.botAdminUserIds.orEmpty(),
			commandPrefix = CommandPrefix.ofString(configJson.commandPrefix),
			commandDefinitions = configJson.commands.orEmpty().mapToCommandDefinitions(),
			scheduledMessages = configJson.scheduledMessages.orEmpty().mapToLocalScheduledMessages(),
		)
	}

	fun transformConfig(config: Config): ConfigJson {
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
			commandPrefix = config.commandPrefix.string,
			commands = config.commandDefinitions
				.associate { commandDefinition: CommandDefinition ->
					commandDefinition.commandName.string to commandDefinition.toCommandDetailsJson()
				}
				.ifEmpty { null },
			scheduledMessages = config.scheduledMessages
				.mapValues { (_: String, localScheduledMessages: Set<LocalScheduledMessage>) ->
					localScheduledMessages
						.mapTo(
							ArrayList(localScheduledMessages.size),
							LocalScheduledMessage::toScheduledMessage,
						)
				}
				.ifEmpty { null },
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
				commandName = CommandName.ofString(commandNameStr),
				action = details.action.mapToAction(),
			)
		}
}

private fun Map<String, List<ScheduledMessage>>.mapToLocalScheduledMessages(): Map<String, Set<LocalScheduledMessage>> {
	return this
		.mapValues { (_: String, scheduledMessages: List<ScheduledMessage>) ->
			scheduledMessages
				.mapTo(
					LinkedHashSet(scheduledMessages.size),
					ScheduledMessage::toLocalScheduledMessage,
				)
		}
}

private fun ScheduledMessage.toLocalScheduledMessage(): LocalScheduledMessage {
	return LocalScheduledMessage(
		utcTime = LocalTime.parse(this.utcTime),
		channelSnowflakeId = this.channelSnowflakeId,
		messageContent = this.messageContent,
	)
}

private fun LocalScheduledMessage.toScheduledMessage(): ScheduledMessage {
	return ScheduledMessage(
		utcTime = this.utcTime.toString(),
		channelSnowflakeId = this.channelSnowflakeId,
		messageContent = this.messageContent,
	)
}

private fun ActionJson.mapToAction(): Action {
	return Action.Response(
		message = this.responseMessage,
	)
}

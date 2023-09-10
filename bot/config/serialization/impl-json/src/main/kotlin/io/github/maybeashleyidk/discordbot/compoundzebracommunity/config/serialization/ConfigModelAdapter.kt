package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandPrefix
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal class ConfigModelAdapter @Suppress("ktlint:standard:annotation") @Inject constructor() {

	@CheckReturnValue
	fun transformConfig(configJson: ConfigJson): Config {
		return Config(
			strings = configJson.strings.toLanguageStrings(),
			botAdminUserIds = configJson.botAdminUserIds.orEmpty(),
			commandPrefix = CommandPrefix.ofString(configJson.commandPrefix),
			commandDefinitions = configJson.commands.orEmpty().mapToCommandDefinitions(),
		)
	}

	@CheckReturnValue
	fun transformConfig(config: Config): ConfigJson {
		return ConfigJson(
			strings = LanguageStringsJson(
				genericInvalidCommandName = config.strings.generic.invalidCommandNameFormat,
				genericUnknownCommand = config.strings.generic.unknownCommandFormat,

				pollTitle = config.strings.poll.titleFormat,
				pollOption = config.strings.poll.optionFormat,
				pollClosedMessage = config.strings.poll.closedMessageFormat,
				pollActionClose = config.strings.poll.action.close,

				commandPollMissingDescription = config.strings.command.poll.missingDescription,
				commandPollLessThan2Options = config.strings.command.poll.lessThan2Options,

				commandQuerypollInsufficientPermissions = config.strings.command.queryPoll.insufficientPermissions,
				commandQuerypollMissingId = config.strings.command.queryPoll.missingId,
				commandQuerypollNoSuchPollWithId = config.strings.command.queryPoll.noSuchPollWithId,

				command8ballMissingQuestion = config.strings.command.magic8Ball.missingQuestion,
				command8ballResponses = config.strings.command.magic8Ball.responses,

				commandGetconfigInsufficientPermissions = config.strings.command.getConfig.insufficientPermissions,

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
		)
	}
}

@CheckReturnValue
private fun CommandDefinition.toCommandDetailsJson(): CommandDetailsJson {
	return CommandDetailsJson(
		action = when (val action: Action = this.action) {
			is Action.Response -> ActionJson(responseMessage = action.message)
		},
	)
}

@CheckReturnValue
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
			getConfig = LanguageStrings.Command.GetConfig(
				insufficientPermissions = this.commandGetconfigInsufficientPermissions,
			),
			shutdown = LanguageStrings.Command.Shutdown(
				response = this.commandShutdownResponse,
				insufficientPermissions = this.commandShutdownInsufficientPermissions,
			),
		),
	)
}

@CheckReturnValue
private fun Map<String, CommandDetailsJson>.mapToCommandDefinitions(): Set<CommandDefinition> {
	return this
		.mapTo(LinkedHashSet(this.size)) { (commandNameStr: String, details: CommandDetailsJson) ->
			CommandDefinition(
				commandName = CommandName.ofString(commandNameStr),
				action = details.action.mapToAction(),
			)
		}
}

@CheckReturnValue
private fun ActionJson.mapToAction(): Action {
	return Action.Response(
		message = this.responseMessage,
	)
}

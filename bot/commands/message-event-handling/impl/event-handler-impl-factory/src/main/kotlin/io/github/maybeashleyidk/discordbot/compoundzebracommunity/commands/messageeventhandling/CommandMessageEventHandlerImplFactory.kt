package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.BuiltInCommandsFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import java.nio.file.Path

public class CommandMessageEventHandlerImplFactory(
	private val configSupplier: ConfigSupplier,
	private val configFilePath: Path,
	// private val emojiStatsManager: EmojiStatsManager,
	private val pollCreator: PollCreator,
	private val pollHolder: PollHolder,
	private val selfTimeoutService: SelfTimeoutService,
	private val shutdownRequester: ShutdownRequester,
	private val botEnvironmentType: BotEnvironmentType,
	private val logger: Logger,
) {

	private fun createBuiltInCommands(): Set<Command> {
		val factory = BuiltInCommandsFactory(
			this.configSupplier,
			this.configFilePath,
			// this.emojiStatsManager,
			this.pollCreator,
			this.pollHolder,
			this.selfTimeoutService,
			this.shutdownRequester,
			this.botEnvironmentType,
			this.logger,
		)

		return factory.create()
	}

	private fun createConfiguredPredefinedResponseCommands(): Set<Command> {
		val config: Config = this.configSupplier.get()

		return config.commandDefinitions
			.mapTo(
				LinkedHashSet(config.commandDefinitions.size),
				CommandDefinition::toPredefinedResponseCommand,
			)
	}

	private fun createCommands(): Set<Command> {
		val builtInCommands: Set<Command> = this.createBuiltInCommands()
		val configuredPredefinedResponseCommands: Set<Command> = this.createConfiguredPredefinedResponseCommands()

		return (builtInCommands + configuredPredefinedResponseCommands)
	}

	public fun create(): CommandMessageEventHandler {
		val commandMessageParser = CommandMessageParser(this.configSupplier)
		val commands: Set<Command> = this.createCommands()

		return CommandMessageEventHandlerImpl(
			commandMessageParser,
			this.configSupplier,
			commands,
			this.logger,
		)
	}
}

private fun CommandDefinition.toPredefinedResponseCommand(): PredefinedResponseCommand {
	return PredefinedResponseCommand(
		this.commandName,
		(this.action as Action.Response).message,
	)
}

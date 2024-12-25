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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path

public class CommandsMessageEventHandlingImplModule(
	scope: DiScope,
	private val configSupplier: ConfigSupplier,
	configFilePath: Path,
	// emojiStatsManager: EmojiStatsManager,
	pollCreator: PollCreator,
	pollHolder: PollHolder,
	selfTimeoutService: SelfTimeoutService,
	shutdownRequester: ShutdownRequester,
	botEnvironmentType: BotEnvironmentType,
	private val logger: Logger,
) : DiModule(scope) {

	private val commandMessageParser: CommandMessageParser
		get() {
			return CommandMessageParser(this.configSupplier)
		}

	private val builtInCommandsFactory: BuiltInCommandsFactory by this.reusable {
		BuiltInCommandsFactory(
			this.configSupplier,
			configFilePath,
			// emojiStatsManager,
			pollCreator,
			pollHolder,
			selfTimeoutService,
			shutdownRequester,
			botEnvironmentType,
			this.logger,
		)
	}

	private val configuredPredefinedResponseCommands: Set<Command>
		get() {
			val config: Config = this.configSupplier.get()

			return config.commandDefinitions
				.mapTo(
					LinkedHashSet(config.commandDefinitions.size),
					CommandDefinition::toPredefinedResponseCommand,
				)
		}

	private val commands: Set<Command>
		get() {
			val builtInCommands: Set<Command> = this.builtInCommandsFactory.create()
			return (builtInCommands + this.configuredPredefinedResponseCommands)
		}

	public val commandMessageEventHandlerImpl: CommandMessageEventHandlerImpl
		get() {
			return CommandMessageEventHandlerImpl(
				this.commandMessageParser,
				this.configSupplier,
				this.commands,
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

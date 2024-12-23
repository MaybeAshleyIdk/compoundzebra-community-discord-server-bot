package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.BuiltInCommandsModule
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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.Provider
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.getValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path

public class CommandsMessageEventHandlingImplModule(
	scope: DiScope,
	configSupplier: Provider<ConfigSupplier>,
	configFilePath: Provider<Path>,
	// emojiStatsManager: Provider<EmojiStatsManager>,
	pollCreator: Provider<PollCreator>,
	pollHolder: Provider<PollHolder>,
	selfTimeoutService: Provider<SelfTimeoutService>,
	shutdownRequester: Provider<ShutdownRequester>,
	botEnvironmentType: Provider<BotEnvironmentType>,
	logger: Provider<Logger>,
) : DiModule(scope) {

	private val configSupplier: ConfigSupplier by configSupplier
	private val logger: Logger by logger

	private val commandMessageParser: CommandMessageParser
		get() {
			return CommandMessageParser(this.configSupplier)
		}

	private val builtInCommandsModule: BuiltInCommandsModule by this.reusable {
		BuiltInCommandsModule(
			scope,
			configSupplier,
			configFilePath,
			// emojiStatsManager,
			pollCreator,
			pollHolder,
			selfTimeoutService,
			shutdownRequester,
			botEnvironmentType,
			logger,
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
			return (this.builtInCommandsModule.builtInCommands + this.configuredPredefinedResponseCommands)
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

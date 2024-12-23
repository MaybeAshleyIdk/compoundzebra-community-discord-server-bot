package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.Multibinds
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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path
import javax.inject.Provider

@Module(
	includes = [
		CommandsMessageEventHandlingImplModule.Bindings::class,
	],
)
public object CommandsMessageEventHandlingImplModule {

	@Module
	internal interface Bindings {

		@Multibinds
		fun multibindCommands(): Set<@JvmSuppressWildcards Command>
	}

	@Provides
	@Reusable
	internal fun provideBuiltInCommandsModule(
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
	): BuiltInCommandsModule {
		return BuiltInCommandsModule(
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

	@Provides
	@ElementsIntoSet
	internal fun provideBuiltInCommands(builtInCommandsModule: BuiltInCommandsModule): Set<Command> {
		return builtInCommandsModule.builtInCommands
	}

	// TODO: find a way to do this dynamic
	@Provides
	@ElementsIntoSet
	internal fun provideConfiguredPredefinedResponseCommands(configSupplier: ConfigSupplier): Set<Command> {
		val config: Config = configSupplier.get()
		return config.commandDefinitions
			.mapTo(
				LinkedHashSet(config.commandDefinitions.size),
				CommandDefinition::toPredefinedResponseCommand,
			)
	}
}

private fun CommandDefinition.toPredefinedResponseCommand(): PredefinedResponseCommand {
	return PredefinedResponseCommand(
		this.commandName,
		(this.action as Action.Response).message,
	)
}

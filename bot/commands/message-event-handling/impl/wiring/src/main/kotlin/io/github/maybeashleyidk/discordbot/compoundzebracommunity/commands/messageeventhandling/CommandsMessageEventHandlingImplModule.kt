package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.BuiltInCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier

@Module(
	includes = [
		CommandsMessageEventHandlingImplModule.Bindings::class,
		BuiltInCommandsModule::class,
	],
)
public object CommandsMessageEventHandlingImplModule {

	@Module
	internal interface Bindings {

		@Multibinds
		fun multibindCommands(): Set<@JvmSuppressWildcards Command>
	}

	// TODO: find a way to do this dynamic
	@Provides
	@ElementsIntoSet
	internal fun provideConfiguredPredefinedResponseCommands(
		predefinedResponseCommandFactory: PredefinedResponseCommand.Factory,
		configSupplier: ConfigSupplier,
	): Set<Command> {
		val config: Config = configSupplier.get()
		return config.commandDefinitions
			.mapTo(
				LinkedHashSet(config.commandDefinitions.size),
				predefinedResponseCommandFactory::buildFromCommandDefinition,
			)
	}
}

private fun PredefinedResponseCommand.Factory.buildFromCommandDefinition(
	commandDefinition: CommandDefinition,
): PredefinedResponseCommand {
	return this
		.build(
			commandDefinition.commandName,
			(commandDefinition.action as Action.Response).message,
		)
}

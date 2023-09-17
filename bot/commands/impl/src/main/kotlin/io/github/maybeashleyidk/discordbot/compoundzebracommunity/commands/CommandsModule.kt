package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.BuiltinCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Action
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.CommandDefinition
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import net.dv8tion.jda.api.hooks.EventListener

@Module(
	includes = [
		CommandsModule.Bindings::class,
		BuiltinCommandsModule::class,
	],
)
public object CommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindCommandEventListener(commandEventListener: CommandEventListener): EventListener

		@Multibinds
		fun multibindCommands(): Set<@JvmSuppressWildcards Command>
	}

	// TODO: find a way to do this dynamic
	@Provides
	@ElementsIntoSet
	internal fun provideConfiguredEchoCommands(
		echoCommandFactory: EchoCommand.Factory,
		configSupplier: ConfigSupplier,
	): Set<Command> {
		val config: Config = configSupplier.get()
		return config.commandDefinitions
			.mapTo(LinkedHashSet(config.commandDefinitions.size), echoCommandFactory::buildFromCommandDefinition)
	}
}

private fun EchoCommand.Factory.buildFromCommandDefinition(commandDefinition: CommandDefinition): EchoCommand {
	return this
		.build(
			commandDefinition.commandName,
			(commandDefinition.action as Action.Response).message,
		)
}

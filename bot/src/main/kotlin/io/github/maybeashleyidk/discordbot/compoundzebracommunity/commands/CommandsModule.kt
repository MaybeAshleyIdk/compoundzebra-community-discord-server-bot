package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls.di.PollCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.EchoCommandDefinition
import net.dv8tion.jda.api.hooks.EventListener
import javax.annotation.CheckReturnValue

@Module(
	includes = [
		CommandsModule.Bindings::class,
		PollCommandsModule::class,
	],
)
internal object CommandsModule {

	@Module
	interface Bindings {

		@Binds
		@IntoSet
		fun bindCommandEventListener(commandEventListener: CommandEventListener): EventListener

		@Multibinds
		fun multibindCommands(): Set<@JvmSuppressWildcards Command>

		@Binds
		@IntoSet
		fun bindGetConfigCommand(getConfigCommand: GetConfigCommand): Command

		@Binds
		@IntoSet
		fun bindShutdownCommand(shutdownCommand: ShutdownCommand): Command
	}

	// TODO: find a way to do this dynamic
	@Provides
	@ElementsIntoSet
	fun provideConfiguredEchoCommands(
		echoCommandFactory: EchoCommand.Factory,
		configLoader: ConfigLoader,
	): Set<Command> {
		val config: Config = configLoader.load()
		return config.echoCommandDefinitions
			.mapTo(LinkedHashSet(config.echoCommandDefinitions.size), echoCommandFactory::buildFromCommandDefinition)
	}
}

@CheckReturnValue
private fun EchoCommand.Factory.buildFromCommandDefinition(echoCommandDefinition: EchoCommandDefinition): EchoCommand {
	return this
		.build(
			echoCommandDefinition.commandName,
			echoCommandDefinition.responseMessage,
		)
}

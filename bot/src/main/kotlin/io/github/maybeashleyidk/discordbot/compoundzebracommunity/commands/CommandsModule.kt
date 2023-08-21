package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.EchoCommandDefinition

@Module(
	includes = [
		CommandsModule.Bindings::class,
	],
)
internal object CommandsModule {

	@Module
	interface Bindings {

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
			.mapTo(LinkedHashSet(config.echoCommandDefinitions.size)) { echoCommandDefinition: EchoCommandDefinition ->
				echoCommandFactory
					.build(
						name = CommandName.ofString(echoCommandDefinition.commandNameStr),
						echoCommandDefinition.responseMessage,
					)
			}
	}
}

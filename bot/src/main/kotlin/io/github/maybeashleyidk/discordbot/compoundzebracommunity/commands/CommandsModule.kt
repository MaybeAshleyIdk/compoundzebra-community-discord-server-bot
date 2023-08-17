package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds

@Module(
	includes = [
		CommandsModule.Bindings::class,
	],
)
object CommandsModule {

	@Module
	interface Bindings {

		@Multibinds
		fun multibindCommands(): Set<@JvmSuppressWildcards Command>

		@Binds
		@IntoSet
		fun bindShutdownCommand(shutdownCommand: ShutdownCommand): Command
	}

	@Provides
	@IntoSet
	fun providePingCommand(echoCommandFactory: EchoCommand.Factory): Command {
		return echoCommandFactory
			.build(
				nameString = "ping",
				responseMessage = "pong",
			)
	}
}

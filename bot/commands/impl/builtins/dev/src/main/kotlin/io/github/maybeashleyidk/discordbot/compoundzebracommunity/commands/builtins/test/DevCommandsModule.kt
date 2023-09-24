package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.test

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command

@Module(includes = [DevCommandsModule.Bindings::class])
public object DevCommandsModule {

	@Module
	internal interface Bindings {

		@Multibinds
		@DevCommand
		fun multibindDevCommands(): Set<@JvmSuppressWildcards Command>

		@Binds
		@DevCommand
		@IntoSet
		fun bindDevCommand(devTestCommand: DevTestCommand): Command
	}

	@Provides
	@ElementsIntoSet
	internal fun provideCommandsFromDevCommands(
		botEnvironmentType: BotEnvironmentType,
		@DevCommand devCommands: Set<@JvmSuppressWildcards Command>,
	): Set<@JvmSuppressWildcards Command> {
		return when (botEnvironmentType) {
			BotEnvironmentType.DEVELOPMENT -> devCommands
			BotEnvironmentType.PRODUCTION -> emptySet()
		}
	}
}

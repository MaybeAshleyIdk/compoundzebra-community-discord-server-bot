package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.shutdown.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.shutdown.ShutdownCommand

@Module(includes = [ShutdownCommandsModule.Bindings::class])
public object ShutdownCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindShutdownCommand(shutdownCommand: ShutdownCommand): Command
	}
}

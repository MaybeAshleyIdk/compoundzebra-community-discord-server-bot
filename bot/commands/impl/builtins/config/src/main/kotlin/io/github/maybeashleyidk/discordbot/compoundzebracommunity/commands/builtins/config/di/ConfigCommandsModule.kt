package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.config.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.config.GetConfigCommand

@Module(includes = [ConfigCommandsModule.Bindings::class])
public object ConfigCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindGetConfigCommand(getConfigCommand: GetConfigCommand): Command
	}
}

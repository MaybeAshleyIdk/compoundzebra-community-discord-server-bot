package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.rng.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.rng.RngCommand

@Module(includes = [RngCommandsModule.Bindings::class])
public class RngCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindRngCommand(rngCommand: RngCommand): Command
	}
}

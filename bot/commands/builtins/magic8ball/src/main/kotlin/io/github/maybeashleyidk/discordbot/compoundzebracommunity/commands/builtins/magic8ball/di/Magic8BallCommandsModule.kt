package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.magic8ball.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.magic8ball.Magic8BallCommand

@Module(includes = [Magic8BallCommandsModule.Bindings::class])
public object Magic8BallCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindMagic8BallCommand(magic8BallCommand: Magic8BallCommand): Command
	}
}

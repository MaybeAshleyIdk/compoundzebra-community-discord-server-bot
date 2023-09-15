package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.polls.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.polls.PollCreationCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.polls.PollQueryCommand

@Module(includes = [PollCommandsModule.Bindings::class])
public object PollCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindPollCreationCommand(pollCreationCommand: PollCreationCommand): Command

		@Binds
		@IntoSet
		fun bindPollQueryCommand(pollQueryCommand: PollQueryCommand): Command
	}
}

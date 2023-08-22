package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls.PollCreationCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls.PollQueryCommand

@Module
internal interface PollCommandsModule {

	@Binds
	@IntoSet
	fun bindPollCreationCommand(pollCreationCommand: PollCreationCommand): Command

	@Binds
	@IntoSet
	fun bindPollQueryCommand(pollQueryCommand: PollQueryCommand): Command
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification

import dagger.Binds
import dagger.Module

@Module(includes = [PollsModificationModule.Bindings::class])
public object PollsModificationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollModifier(pollManagerModifier: PollManagerModifier): PollModifier
	}
}

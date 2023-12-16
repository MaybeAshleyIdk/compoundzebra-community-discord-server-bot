package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification

import dagger.Binds
import dagger.Module

@Module(includes = [PollModificationModule.Bindings::class])
public object PollModificationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollModifier(pollManagerModifier: PollManagerModifier): PollModifier
	}
}

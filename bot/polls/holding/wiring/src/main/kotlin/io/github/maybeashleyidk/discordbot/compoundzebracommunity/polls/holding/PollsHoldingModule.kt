package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding

import dagger.Binds
import dagger.Module

@Module(includes = [PollsHoldingModule.Bindings::class])
public object PollsHoldingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollHolder(pollManagerHolder: PollManagerHolder): PollHolder
	}
}

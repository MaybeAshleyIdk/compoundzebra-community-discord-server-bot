package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollholding

import dagger.Binds
import dagger.Module

@Module(includes = [PollHoldingModule.Bindings::class])
public object PollHoldingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollHolder(pollManagerHolder: PollManagerHolder): PollHolder
	}
}

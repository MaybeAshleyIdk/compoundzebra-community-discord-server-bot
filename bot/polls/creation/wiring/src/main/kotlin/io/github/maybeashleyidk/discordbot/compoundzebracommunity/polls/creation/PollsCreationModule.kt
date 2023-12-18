package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation

import dagger.Binds
import dagger.Module

@Module(includes = [PollsCreationModule.Bindings::class])
public object PollsCreationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollCreator(pollManagerCreator: PollManagerCreator): PollCreator
	}
}

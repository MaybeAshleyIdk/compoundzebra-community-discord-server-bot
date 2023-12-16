package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation

import dagger.Binds
import dagger.Module

@Module(includes = [PollCreationModule.Bindings::class])
public object PollCreationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollCreator(pollManagerCreator: PollManagerCreator): PollCreator
	}
}

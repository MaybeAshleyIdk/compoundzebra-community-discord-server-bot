package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation

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

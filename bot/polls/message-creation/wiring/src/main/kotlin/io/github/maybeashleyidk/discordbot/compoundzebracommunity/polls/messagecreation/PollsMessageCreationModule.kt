package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.messagecreation

import dagger.Binds
import dagger.Module

@Module(includes = [PollsMessageCreationModule.Bindings::class])
public object PollsMessageCreationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollMessageCreator(impl: PollMessageCreatorImpl): PollMessageCreator
	}
}

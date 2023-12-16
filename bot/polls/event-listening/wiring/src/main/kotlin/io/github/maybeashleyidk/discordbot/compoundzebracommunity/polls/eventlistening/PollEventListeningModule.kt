package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventlistening

import dagger.Binds
import dagger.Module

@Module(includes = [PollEventListeningModule.Bindings::class])
public object PollEventListeningModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollEventHandler(impl: PollEventHandlerImpl): PollEventHandler
	}
}

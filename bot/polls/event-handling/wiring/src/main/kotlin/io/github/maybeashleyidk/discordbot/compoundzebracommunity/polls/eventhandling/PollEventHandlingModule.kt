package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling

import dagger.Binds
import dagger.Module

@Module(includes = [PollEventHandlingModule.Bindings::class])
public object PollEventHandlingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollEventHandler(impl: PollEventHandlerImpl): PollEventHandler
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling

import dagger.Binds
import dagger.Module

@Module(includes = [PollsEventHandlingModule.Bindings::class])
public object PollsEventHandlingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollEventHandler(impl: PollEventHandlerImpl): PollEventHandler
	}
}

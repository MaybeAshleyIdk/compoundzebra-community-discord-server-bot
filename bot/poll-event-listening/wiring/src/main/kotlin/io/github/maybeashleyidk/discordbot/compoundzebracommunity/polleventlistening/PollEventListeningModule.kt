package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening

import dagger.Binds
import dagger.Module

@Module(includes = [PollEventListeningModule.Bindings::class])
public object PollEventListeningModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollEventListener(impl: PollEventListenerImpl): PollEventListener
	}
}

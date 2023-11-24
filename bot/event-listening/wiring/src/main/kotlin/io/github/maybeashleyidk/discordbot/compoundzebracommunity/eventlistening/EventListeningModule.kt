package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening

import dagger.Binds
import dagger.Module

@Module(includes = [EventListeningModule.Bindings::class])
public object EventListeningModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindMainEventListener(impl: MainEventListenerImpl): MainEventListener
	}
}

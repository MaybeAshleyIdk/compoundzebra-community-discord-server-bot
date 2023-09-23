package io.github.maybeashleyidk.discordbot.compoundzebracommunity.scheduledmessages

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ScheduledMessagesModule.Bindings::class])
public object ScheduledMessagesModule {

	@Module
	internal interface Bindings {

		@Binds
		@Singleton
		fun bindScheduledMessagesManager(impl: ScheduledMessagesManagerImpl): ScheduledMessagesManager
	}
}

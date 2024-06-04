package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages

import dagger.Binds
import dagger.Module

@Module(includes = [RecentMessagesModule.Bindings::class])
public object RecentMessagesModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindRecentMessagesService(impl: RecentMessagesServiceImpl): RecentMessagesService
	}
}

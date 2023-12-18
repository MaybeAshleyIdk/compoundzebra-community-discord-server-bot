package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management

import dagger.Binds
import dagger.Module

@Module(includes = [PollsManagementModule.Bindings::class])
public object PollsManagementModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollManager(impl: PollManagerImpl): PollManager
	}
}

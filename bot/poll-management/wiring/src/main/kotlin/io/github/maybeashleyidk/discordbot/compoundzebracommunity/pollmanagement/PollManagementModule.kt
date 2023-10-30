package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement

import dagger.Binds
import dagger.Module

@Module(includes = [PollManagementModule.Bindings::class])
public object PollManagementModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollManager(impl: PollManagerImpl): PollManager
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import dagger.Binds
import dagger.Module

@Module(includes = [SelfTimeoutModule.Bindings::class])
public object SelfTimeoutModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindSelfTimeoutService(impl: SelfTimeoutServiceImpl): SelfTimeoutService
	}
}

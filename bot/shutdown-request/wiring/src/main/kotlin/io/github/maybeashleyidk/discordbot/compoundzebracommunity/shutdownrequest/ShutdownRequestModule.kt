package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownrequest

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownRequestModule.Bindings::class])
public object ShutdownRequestModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownRequester(shutdownManagerRequester: ShutdownManagerRequester): ShutdownRequester
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownRequestingModule.Bindings::class])
public object ShutdownRequestingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownRequester(shutdownManagerRequester: ShutdownManagerRequester): ShutdownRequester
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting

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

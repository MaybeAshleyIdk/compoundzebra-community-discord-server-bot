package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownRequestFeatureModule.Bindings::class])
public object ShutdownRequestFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownRequester(shutdownManagerRequester: ShutdownManagerRequester): ShutdownRequester
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownFeatureModule.Bindings::class])
public object ShutdownFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownManager(semaphoreShutdownManager: SemaphoreShutdownManager): ShutdownManager

		@Binds
		fun bindShutdownRequester(semaphoreShutdownManager: SemaphoreShutdownManager): ShutdownRequester
	}
}

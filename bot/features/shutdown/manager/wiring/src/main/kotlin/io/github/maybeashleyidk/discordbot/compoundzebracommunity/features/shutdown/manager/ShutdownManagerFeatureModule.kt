package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownManagerFeatureModule.Bindings::class])
public object ShutdownManagerFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownManager(semaphoreShutdownManager: SemaphoreShutdownManager): ShutdownManager
	}
}

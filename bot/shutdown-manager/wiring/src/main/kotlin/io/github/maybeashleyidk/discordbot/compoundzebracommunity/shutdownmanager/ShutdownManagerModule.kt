package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownManagerModule.Bindings::class])
public object ShutdownManagerModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownManager(semaphoreShutdownManager: SemaphoreShutdownManager): ShutdownManager
	}
}

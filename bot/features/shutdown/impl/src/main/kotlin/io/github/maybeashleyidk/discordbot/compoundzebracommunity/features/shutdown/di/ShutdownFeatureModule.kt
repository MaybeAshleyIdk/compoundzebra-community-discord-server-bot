package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.SemaphoreShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.ShutdownRequester

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

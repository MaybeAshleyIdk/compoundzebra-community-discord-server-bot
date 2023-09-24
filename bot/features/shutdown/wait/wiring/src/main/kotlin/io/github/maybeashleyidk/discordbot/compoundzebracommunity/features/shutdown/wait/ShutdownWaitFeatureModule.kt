package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownWaitFeatureModule.Bindings::class])
public object ShutdownWaitFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownAwaiter(shutdownManagerAwaiter: ShutdownManagerAwaiter): ShutdownAwaiter
	}
}

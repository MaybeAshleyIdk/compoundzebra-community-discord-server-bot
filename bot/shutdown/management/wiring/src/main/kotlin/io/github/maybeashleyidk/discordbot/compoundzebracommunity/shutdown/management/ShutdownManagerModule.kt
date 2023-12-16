package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownManagerModule.Bindings::class])
public object ShutdownManagerModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownManager(impl: ShutdownManagerImpl): ShutdownManager
	}
}

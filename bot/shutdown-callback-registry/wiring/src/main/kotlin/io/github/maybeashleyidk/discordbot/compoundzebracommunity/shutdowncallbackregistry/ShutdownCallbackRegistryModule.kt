package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbackregistry

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownCallbackRegistryModule.Bindings::class])
public object ShutdownCallbackRegistryModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownCallbackRegistry(
			shutdownManagerCallbackRegistry: ShutdownManagerCallbackRegistry,
		): ShutdownCallbackRegistry
	}
}

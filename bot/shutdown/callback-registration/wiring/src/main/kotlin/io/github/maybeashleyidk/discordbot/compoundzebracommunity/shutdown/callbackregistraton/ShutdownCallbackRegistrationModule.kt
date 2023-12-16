package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownCallbackRegistrationModule.Bindings::class])
public object ShutdownCallbackRegistrationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownCallbackRegistry(
			shutdownManagerCallbackRegistry: ShutdownManagerCallbackRegistry,
		): ShutdownCallbackRegistry
	}
}

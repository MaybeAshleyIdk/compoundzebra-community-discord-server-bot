package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownEventHandlingModule.Bindings::class])
public object ShutdownEventHandlingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownEventHandler(shutdownManagerEventHandler: ShutdownManagerEventHandler): ShutdownEventHandler
	}
}

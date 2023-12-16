package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownManagementModule.Bindings::class])
public object ShutdownManagementModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownManager(impl: ShutdownManagerImpl): ShutdownManager
	}
}

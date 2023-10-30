package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownwait

import dagger.Binds
import dagger.Module

@Module(includes = [ShutdownWaitModule.Bindings::class])
public object ShutdownWaitModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindShutdownAwaiter(shutdownManagerAwaiter: ShutdownManagerAwaiter): ShutdownAwaiter
	}
}

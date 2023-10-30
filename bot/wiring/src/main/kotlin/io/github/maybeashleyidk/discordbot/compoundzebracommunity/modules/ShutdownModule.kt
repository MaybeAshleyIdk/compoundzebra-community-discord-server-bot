package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager.ShutdownManagerModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownrequest.ShutdownRequestModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownwait.ShutdownWaitModule

@Module(
	includes = [
		ShutdownManagerModule::class,
		ShutdownWaitModule::class,
		ShutdownRequestModule::class,
	],
)
internal object ShutdownModule

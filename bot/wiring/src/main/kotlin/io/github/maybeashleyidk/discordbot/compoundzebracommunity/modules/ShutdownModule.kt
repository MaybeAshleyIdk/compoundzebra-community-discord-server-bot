package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager.ShutdownManagerModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request.ShutdownRequestModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait.ShutdownWaitModule

@Module(
	includes = [
		ShutdownManagerModule::class,
		ShutdownWaitModule::class,
		ShutdownRequestModule::class,
	],
)
internal object ShutdownModule

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager.ShutdownManagerFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request.ShutdownRequestFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait.ShutdownWaitFeatureModule

@Module(
	includes = [
		ShutdownManagerFeatureModule::class,
		ShutdownRequestFeatureModule::class,
		ShutdownWaitFeatureModule::class,
	],
)
public object ShutdownFeatureModule

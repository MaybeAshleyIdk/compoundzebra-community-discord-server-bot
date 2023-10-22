package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager.ShutdownManagerFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request.ShutdownRequestFeatureModule

@Module(
	includes = [
		ShutdownManagerFeatureModule::class,
		ShutdownRequestFeatureModule::class,
	],
)
public object ShutdownFeatureModule

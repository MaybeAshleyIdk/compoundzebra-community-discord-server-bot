package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager.ShutdownManagerFeatureModule

@Module(
	includes = [
		ShutdownManagerFeatureModule::class,
	],
)
public object ShutdownFeatureModule

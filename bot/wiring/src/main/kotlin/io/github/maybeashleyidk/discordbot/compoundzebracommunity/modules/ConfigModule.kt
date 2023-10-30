package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCacheModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigSerializationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigSourceModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplierModule

@Module(
	includes = [
		ConfigSerializationModule::class,
		ConfigSourceModule::class,
		ConfigCacheModule::class,
		ConfigSupplierModule::class,
	],
)
internal object ConfigModule

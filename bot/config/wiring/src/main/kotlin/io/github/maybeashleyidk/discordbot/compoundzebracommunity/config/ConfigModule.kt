package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.ConfigCacheModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigSerializationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.ConfigSourceModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplierModule

@Module(
	includes = [
		ConfigSerializationModule::class,
		ConfigSourceModule::class,
		ConfigCacheModule::class,
		ConfigSupplierModule::class,
	],
)
public object ConfigModule

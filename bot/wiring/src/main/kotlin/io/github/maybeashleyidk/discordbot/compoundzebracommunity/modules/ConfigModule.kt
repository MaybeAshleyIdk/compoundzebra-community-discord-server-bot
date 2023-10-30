package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

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
internal object ConfigModule

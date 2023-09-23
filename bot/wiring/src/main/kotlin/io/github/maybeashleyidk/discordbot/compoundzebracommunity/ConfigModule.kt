package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.di.ConfigCacheModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.di.ConfigSerializationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.di.ConfigSourceModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.di.ConfigSupplierModule

@Module(
	includes = [
		ConfigSupplierModule::class,
		ConfigCacheModule::class,
		ConfigSourceModule::class,
		ConfigSerializationModule::class,
	],
)
internal object ConfigModule

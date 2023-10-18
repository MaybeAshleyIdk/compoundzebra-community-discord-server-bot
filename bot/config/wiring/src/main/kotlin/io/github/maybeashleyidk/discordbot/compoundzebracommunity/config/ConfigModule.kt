package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigSerializationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.ConfigSourceModule

@Module(
	includes = [
		ConfigSerializationModule::class,
		ConfigSourceModule::class,
	],
)
public object ConfigModule

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigSerializationModule

@Module(
	includes = [
		ConfigSerializationModule::class,
	],
)
public object ConfigModule

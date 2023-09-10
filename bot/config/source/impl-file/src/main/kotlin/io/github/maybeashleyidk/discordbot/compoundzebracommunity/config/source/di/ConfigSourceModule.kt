package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.ConfigFileManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.ConfigSource

@Module(
	includes = [
		ConfigSourceModule.Bindings::class,
	],
)
public object ConfigSourceModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigFileManager(configFileManager: ConfigFileManager): ConfigSource
	}
}

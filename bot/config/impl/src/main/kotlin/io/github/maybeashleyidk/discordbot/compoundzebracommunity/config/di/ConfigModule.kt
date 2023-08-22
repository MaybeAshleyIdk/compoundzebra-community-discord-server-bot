package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoaderImpl

@Module(
	includes = [
		ConfigModule.Bindings::class,
	],
)
public object ConfigModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigLoader(configLoaderImpl: ConfigLoaderImpl): ConfigLoader
	}
}

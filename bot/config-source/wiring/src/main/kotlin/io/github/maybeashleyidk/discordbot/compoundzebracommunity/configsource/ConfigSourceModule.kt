package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource

import dagger.Binds
import dagger.Module

@Module(includes = [ConfigSourceModule.Bindings::class])
public object ConfigSourceModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigSource(configFileManager: ConfigFileManager): ConfigSource
	}
}

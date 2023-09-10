package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigJsonSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigSerializer

@Module(
	includes = [
		ConfigSerializationModule.Bindings::class,
	],
)
public object ConfigSerializationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigJsonSerializer(configJsonSerializer: ConfigJsonSerializer): ConfigSerializer
	}
}

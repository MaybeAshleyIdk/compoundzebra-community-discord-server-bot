package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization

import dagger.Binds
import dagger.Module

@Module(includes = [ConfigSerializationModule.Bindings::class])
public object ConfigSerializationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigSerializer(configJsonSerializer: ConfigJsonSerializer): ConfigSerializer
	}
}

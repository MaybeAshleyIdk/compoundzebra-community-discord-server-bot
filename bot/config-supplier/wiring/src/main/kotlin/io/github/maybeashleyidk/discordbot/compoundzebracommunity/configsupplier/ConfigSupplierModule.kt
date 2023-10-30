package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier

import dagger.Binds
import dagger.Module

@Module(includes = [ConfigSupplierModule.Bindings::class])
public object ConfigSupplierModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigSupplier(configCacheSupplier: ConfigCacheSupplier): ConfigSupplier
	}
}

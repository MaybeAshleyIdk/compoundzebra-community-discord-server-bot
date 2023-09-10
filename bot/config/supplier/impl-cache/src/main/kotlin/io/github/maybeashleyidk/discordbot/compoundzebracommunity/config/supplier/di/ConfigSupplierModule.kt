package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigCacheSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier

@Module(
	includes = [
		ConfigSupplierModule.Bindings::class,
	],
)
public object ConfigSupplierModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConfigCacheSupplier(configCacheSupplier: ConfigCacheSupplier): ConfigSupplier
	}
}

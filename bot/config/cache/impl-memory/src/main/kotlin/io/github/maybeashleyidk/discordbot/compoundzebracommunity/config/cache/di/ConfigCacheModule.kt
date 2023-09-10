package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.ConfigCache
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.MemoryConfigCache

@Module(
	includes = [
		ConfigCacheModule.Bindings::class,
	],
)
public object ConfigCacheModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindMemoryConfigCache(memoryConfigCache: MemoryConfigCache): ConfigCache
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ConfigCacheModule.Bindings::class])
public object ConfigCacheModule {

	@Module
	internal interface Bindings {

		@Binds
		@Singleton
		fun bindConfigCache(memoryConfigCache: MemoryConfigCache): ConfigCache
	}
}

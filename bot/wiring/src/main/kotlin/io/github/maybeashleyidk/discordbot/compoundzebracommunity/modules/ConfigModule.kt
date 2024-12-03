package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCacheModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigJsonSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigSourceModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplierModule

@Module(
	includes = [
		ConfigSourceModule::class,
		ConfigCacheModule::class,
		ConfigSupplierModule::class,
	],
)
internal object ConfigModule {

	@Provides
	fun provideConfigSerializer(moshi: Moshi): ConfigSerializer {
		return ConfigJsonSerializer(moshi)
	}
}

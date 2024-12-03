package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCache
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.MemoryConfigCache
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigJsonSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigFileManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigSource
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigCacheSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import java.nio.file.Path
import javax.inject.Singleton

@Module
internal object ConfigModule {

	@Provides
	fun provideConfigSerializer(moshi: Moshi): ConfigSerializer {
		return ConfigJsonSerializer(moshi)
	}

	@Provides
	fun provideConfigSource(
		configSerializer: ConfigSerializer,
		botEnvironmentType: BotEnvironmentType,
		configFilePath: Path,
	): ConfigSource {
		return ConfigFileManager(
			configSerializer,
			botEnvironmentType,
			configFilePath,
		)
	}

	@Provides
	@Singleton
	fun provideConfigCache(configSource: ConfigSource, logger: Logger): ConfigCache {
		return MemoryConfigCache(configSource, logger)
	}

	@Provides
	fun provideConfigSupplier(configCache: ConfigCache): ConfigSupplier {
		return ConfigCacheSupplier(configCache)
	}
}

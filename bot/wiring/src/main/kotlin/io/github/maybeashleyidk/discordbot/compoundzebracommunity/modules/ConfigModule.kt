package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCacheModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigJsonSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigFileManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigSource
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplierModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import java.nio.file.Path

@Module(
	includes = [
		ConfigCacheModule::class,
		ConfigSupplierModule::class,
	],
)
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
}

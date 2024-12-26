package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import com.squareup.moshi.Moshi
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.buildtype.BotBuildType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCache
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.MemoryConfigCache
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigJsonSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigFileManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigSource
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigCacheSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path

internal class ConfigModule(
	scope: DiScope,
	private val moshi: Moshi,
	private val botBuildType: BotBuildType,
	private val configFilePath: Path,
	private val logger: Logger,
) : DiModule(scope) {

	private val configSerializer: ConfigSerializer
		get() {
			return ConfigJsonSerializer(this.moshi)
		}

	private val configSource: ConfigSource
		get() {
			return ConfigFileManager(this.configSerializer, this.botBuildType, this.configFilePath)
		}

	private val configCache: ConfigCache by this.singleton {
		MemoryConfigCache(this.configSource, this.logger)
	}

	val configSupplier: ConfigSupplier by this.reusable {
		ConfigCacheSupplier(this.configCache)
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCache
import javax.inject.Inject

public class ConfigCacheSupplier @Inject constructor(
	private val configCache: ConfigCache,
) : ConfigSupplier {

	override fun get(): Config {
		return this.configCache.getValue()
	}
}

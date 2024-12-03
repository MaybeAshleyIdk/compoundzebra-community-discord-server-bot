package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache.ConfigCache

public class ConfigCacheSupplier(private val configCache: ConfigCache) : ConfigSupplier {

	override fun get(): Config {
		return this.configCache.getValue()
	}
}

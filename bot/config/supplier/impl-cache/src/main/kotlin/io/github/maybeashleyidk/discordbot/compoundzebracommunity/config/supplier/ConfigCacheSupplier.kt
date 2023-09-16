package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.ConfigCache
import javax.annotation.CheckReturnValue
import javax.inject.Inject

public class ConfigCacheSupplier @Suppress("ktlint:standard:annotation") @Inject internal constructor(
	private val configCache: ConfigCache,
) : ConfigSupplier {

	@CheckReturnValue
	override fun get(): Config {
		return this.configCache.getValue()
	}
}
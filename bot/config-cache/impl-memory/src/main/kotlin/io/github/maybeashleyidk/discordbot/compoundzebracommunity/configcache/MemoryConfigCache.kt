package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configcache

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource.ConfigSource
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import java.time.Instant
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

public class MemoryConfigCache(
	private val configSource: ConfigSource,
	private val logger: Logger,
) : ConfigCache {

	private data class CachedConfig(
		val config: Config,
		val loadingInstant: Instant,
	)

	private val cachedConfigReadWriteLock: ReadWriteLock = ReentrantReadWriteLock(true)
	private var cachedConfig: CachedConfig? = null

	override fun getValue(): Config {
		val validCachedConfig1: Config? = this.cachedConfigReadWriteLock.readLock()
			.withLock(this::getValidCachedConfigUnsynchronized)

		if (validCachedConfig1 != null) {
			return validCachedConfig1
		}

		return this.cachedConfigReadWriteLock.writeLock()
			.withLock {
				// between the last validity check and now, another thread may have already updated the cached config,
				// so we need to check the validity of the cache again
				val validCachedConfig2: Config? = this.getValidCachedConfigUnsynchronized()
				if (validCachedConfig2 != null) {
					return@withLock validCachedConfig2
				}

				this.logger.logInfo("The config cache is either missing or out of date. Updating it...")

				val newConfig: Config = this.updateCachedConfigUnsynchronized()

				this.logger.logInfo("Updated the config cache successfully")

				newConfig
			}
	}

	private fun getValidCachedConfigUnsynchronized(): Config? {
		val cachedConfigLocal: CachedConfig = this.cachedConfig
			?: return null

		val lastConfigSourceModificationInstant: Instant = this.configSource.getLastModificationInstant()

		if (cachedConfigLocal.loadingInstant < lastConfigSourceModificationInstant) {
			return null
		}

		return cachedConfigLocal.config
	}

	/**
	 * Updates the field [cachedConfig] and also returns the new value.
	 */
	private fun updateCachedConfigUnsynchronized(): Config {
		val loadedConfig: ConfigSource.LoadedConfig = this.configSource.load()

		val cachedConfig =
			CachedConfig(
				config = loadedConfig.config,
				loadingInstant = loadedConfig.loadInstant,
			)

		this.cachedConfig = cachedConfig
		return cachedConfig.config
	}
}

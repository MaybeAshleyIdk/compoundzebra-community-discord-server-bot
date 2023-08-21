package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import okio.BufferedSource
import java.time.Instant
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.annotation.CheckReturnValue
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.withLock

@Singleton
public class ConfigLoader @Suppress("ktlint:standard:annotation") @Inject internal constructor(
	private val configFileManager: ConfigFileManager,
	private val logger: Logger,
	moshi: Moshi,
) {

	private data class CachedConfig(
		val config: Config,
		val loadingInstant: Instant,
	)

	@OptIn(ExperimentalStdlibApi::class)
	private val configJsonAdapter: JsonAdapter<ConfigJson> = moshi.adapter<ConfigJson>()
		.nonNull()

	private val cachedConfigReadWriteLock: ReadWriteLock = ReentrantReadWriteLock(true)
	private var cachedConfig: CachedConfig? = null

	@CheckReturnValue
	public fun load(): Config {
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

	@CheckReturnValue
	private fun getValidCachedConfigUnsynchronized(): Config? {
		val cachedConfigLocal: CachedConfig = this.cachedConfig
			?: return null

		val lastConfigFileModificationInstant: Instant = this.configFileManager.getLastModificationInstant()

		if (cachedConfigLocal.loadingInstant < lastConfigFileModificationInstant) {
			return null
		}

		return cachedConfigLocal.config
	}

	/**
	 * Updates the field [cachedConfig] and also returns the new value.
	 */
	private fun updateCachedConfigUnsynchronized(): Config {
		val readConfig: ConfigFileManager.ReadFile<Config> =
			this.configFileManager.readFile { source: BufferedSource ->
				val configJson: ConfigJson? = this@ConfigLoader.configJsonAdapter.fromJson(source)

				checkNotNull(configJson) {
					"Config is null"
				}

				mapConfigJsonIntoConfig(configJson)
			}

		val cachedConfig =
			CachedConfig(
				config = readConfig.data,
				loadingInstant = readConfig.instant,
			)

		this.cachedConfig = cachedConfig
		return cachedConfig.config
	}
}

@CheckReturnValue
private fun mapConfigJsonIntoConfig(configJson: ConfigJson): Config {
	check(configJson.commandPrefix.isNotBlank()) {
		"Command prefix must not be blank"
	}

	return Config(
		commandPrefix = configJson.commandPrefix,
		strings = configJson.strings.toLanguageStrings(),
		echoCommandDefinitions = configJson.echoCommandDetailsMap.mapToEchoCommandDefinitions(),
		botAdminUserIds = configJson.botAdminUserIds.orEmpty(),
	)
}

@CheckReturnValue
private fun LanguageStringsJson.toLanguageStrings(): LanguageStrings {
	return LanguageStrings(
		generic = LanguageStrings.Generic(
			invalidCommandNameFormat = this.genericInvalidCommandName,
			unknownCommandFormat = this.genericUnknownCommand,
		),
		command = LanguageStrings.Command(
			getConfig = LanguageStrings.Command.GetConfig(
				insufficientPermissions = this.commandGetconfigInsufficientPermissions,
			),
			shutdown = LanguageStrings.Command.Shutdown(
				response = this.commandShutdownResponse,
				insufficientPermissions = this.commandShutdownInsufficientPermissions,
			),
		),
	)
}

@CheckReturnValue
private fun Map<String, EchoCommandDetailsJson>.mapToEchoCommandDefinitions(): Set<EchoCommandDefinition> {
	return this
		.mapTo(LinkedHashSet(this.size)) { (commandNameStr: String, details: EchoCommandDetailsJson) ->
			EchoCommandDefinition(
				commandNameStr = commandNameStr,
				responseMessage = details.responseMessage,
			)
		}
}

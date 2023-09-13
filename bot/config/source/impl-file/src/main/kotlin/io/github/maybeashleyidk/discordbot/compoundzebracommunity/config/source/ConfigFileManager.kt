@file:Suppress("ktlint:standard:import-ordering")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Configs
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigSerializer
import okio.FileMetadata
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import java.time.Instant
import javax.annotation.CheckReturnValue
import javax.inject.Inject
import okio.Path as OkioPath
import java.nio.file.Path as NioPath

/**
 * The functions of this class are not inherently thread-safe.
 */
internal class ConfigFileManager @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configSerializer: ConfigSerializer,
	configFileNioPath: NioPath, // FIXME: this should have a qualifier, put in which module should be put it?
) : ConfigSource {

	private val configFilePath: OkioPath = configFileNioPath.toOkioPath()

	@CheckReturnValue
	override fun getLastModificationInstant(): Instant {
		val metadata: FileMetadata? = FileSystem.SYSTEM.metadataOrNull(this.configFilePath)
		return Instant.ofEpochMilli(metadata?.lastModifiedAtMillis ?: Long.MAX_VALUE)
	}

	override fun load(): ConfigSource.LoadedConfig {
		this.ensureConfigFileExists()

		val instant: Instant = Instant.now()

		val config: Config =
			FileSystem.SYSTEM.read(this.configFilePath) {
				this@ConfigFileManager.configSerializer.deserialize(source = this@read)
			}

		return ConfigSource.LoadedConfig(config, loadInstant = instant)
	}

	private fun ensureConfigFileExists() {
		if (FileSystem.SYSTEM.exists(this.configFilePath)) {
			return
		}

		FileSystem.SYSTEM.write(this.configFilePath) {
			this@ConfigFileManager.configSerializer.serialize(Configs.INITIAL, sink = this@write)
		}
	}
}

@file:Suppress("ktlint:standard:import-ordering")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsource

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Configs
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization.ConfigSerializer
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import okio.FileMetadata
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import java.time.Instant
import okio.Path as OkioPath
import java.nio.file.Path as NioPath

/**
 * The functions of this class are not inherently thread-safe.
 */
public class ConfigFileManager(
	private val configSerializer: ConfigSerializer,
	private val botEnvironmentType: BotEnvironmentType,
	configFileNioPath: NioPath,
) : ConfigSource {

	private val configFilePath: OkioPath = configFileNioPath.toOkioPath()

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
			val initialConfig: Config = Configs.createInitial(this@ConfigFileManager.botEnvironmentType)
			this@ConfigFileManager.configSerializer.serialize(initialConfig, sink = this@write)
		}
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.di.ConfigFilePath
import okio.BufferedSource
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
	@ConfigFilePath configFileNioPath: NioPath,
) {

	data class ReadFile<T>(
		val data: T,
		val instant: Instant,
	)

	private val configFilePath: OkioPath = configFileNioPath.toOkioPath()

	@CheckReturnValue
	fun getLastModificationInstant(): Instant {
		val metadata: FileMetadata? = FileSystem.SYSTEM.metadataOrNull(this.configFilePath)
		return Instant.ofEpochMilli(metadata?.lastModifiedAtMillis ?: Long.MAX_VALUE)
	}

	fun <T> readFile(readerAction: (source: BufferedSource) -> T): ReadFile<T> {
		this.ensureConfigFileExists()

		val instant: Instant = Instant.now()

		val data: T =
			FileSystem.SYSTEM.read(this.configFilePath) {
				readerAction(this@read)
			}

		return ReadFile(data, instant)
	}

	private fun ensureConfigFileExists() {
		if (FileSystem.SYSTEM.exists(this.configFilePath)) {
			return
		}

		DefaultConfigLoader.load { defaultConfigSource: BufferedSource ->
			FileSystem.SYSTEM.write(this@ConfigFileManager.configFilePath) {
				this@write.writeAll(defaultConfigSource)
			}
		}
	}
}

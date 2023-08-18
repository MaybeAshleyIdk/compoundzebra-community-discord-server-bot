package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.Reusable
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import javax.annotation.CheckReturnValue
import javax.inject.Inject

@Reusable
class ConfigLoader @Suppress("ktlint:standard:annotation") @Inject constructor(
	moshi: Moshi,
) {

	private companion object {

		val DEFAULT_CONFIG_JSON_RESOURCE_PATH: Path =
			"/io/github/maybeashleyidk/discordbot/compoundzebracommunity/default_config.json".toPath()

		val CONFIG_JSON_FILE_DEFAULT_PATH: Path = "bot_config.json".toPath()
	}

	@OptIn(ExperimentalStdlibApi::class)
	private val configJsonAdapter: JsonAdapter<Config> = moshi.adapter<Config>()
		.nonNull()

	@CheckReturnValue
	fun load(): Config {
		this.ensureConfigJsonFileExists()

		val config: Config? =
			@Suppress("RemoveRedundantQualifierName")
			FileSystem.SYSTEM.read(ConfigLoader.CONFIG_JSON_FILE_DEFAULT_PATH) {
				this@ConfigLoader.configJsonAdapter.fromJson(this@read)
			}

		checkNotNull(config) {
			"Config is null"
		}

		return config
	}

	private fun ensureConfigJsonFileExists() {
		@Suppress("RemoveRedundantQualifierName")
		if (FileSystem.SYSTEM.exists(ConfigLoader.CONFIG_JSON_FILE_DEFAULT_PATH)) {
			return
		}

		@Suppress("RemoveRedundantQualifierName")
		FileSystem.RESOURCES.read(ConfigLoader.DEFAULT_CONFIG_JSON_RESOURCE_PATH) {
			FileSystem.SYSTEM.write(ConfigLoader.CONFIG_JSON_FILE_DEFAULT_PATH) {
				this@write.writeAll(this@read)
			}
		}
	}
}

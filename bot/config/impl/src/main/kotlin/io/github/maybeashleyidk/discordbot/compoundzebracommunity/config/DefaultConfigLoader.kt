package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import okio.BufferedSource
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

internal object DefaultConfigLoader {

	private val DEFAULT_CONFIG_JSON_RESOURCE_PATH: Path =
		"/io/github/maybeashleyidk/discordbot/compoundzebracommunity/config/default_config.json".toPath()

	fun <T> load(readerAction: (BufferedSource) -> T): T {
		return FileSystem.RESOURCES
			.read(this.DEFAULT_CONFIG_JSON_RESOURCE_PATH) {
				readerAction(this@read)
			}
	}
}

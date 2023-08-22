package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import okio.BufferedSource
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

internal object InitConfigLoader {

	private val INIT_CONFIG_JSON_RESOURCE_PATH: Path =
		"/io/github/maybeashleyidk/discordbot/compoundzebracommunity/config/init_config.json".toPath()

	fun <T> load(readerAction: (BufferedSource) -> T): T {
		return FileSystem.RESOURCES
			.read(this.INIT_CONFIG_JSON_RESOURCE_PATH) {
				readerAction(this@read)
			}
	}
}

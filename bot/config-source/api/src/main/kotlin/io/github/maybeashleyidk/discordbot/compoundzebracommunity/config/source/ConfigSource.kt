package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import java.time.Instant

public interface ConfigSource {

	public data class LoadedConfig(
		public val config: Config,
		public val loadInstant: Instant,
	)

	public fun getLastModificationInstant(): Instant

	public fun load(): LoadedConfig
}

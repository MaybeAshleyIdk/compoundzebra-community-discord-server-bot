package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import java.time.Instant
import javax.annotation.CheckReturnValue

public interface ConfigSource {

	public data class LoadedConfig(
		public val config: Config,
		public val loadInstant: Instant,
	)

	@CheckReturnValue
	public fun getLastModificationInstant(): Instant

	@CheckReturnValue
	public fun load(): LoadedConfig
}

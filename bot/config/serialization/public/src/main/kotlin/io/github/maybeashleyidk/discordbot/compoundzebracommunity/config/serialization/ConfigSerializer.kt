package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import okio.BufferedSink
import okio.BufferedSource

public interface ConfigSerializer {

	public fun serialize(config: Config, sink: BufferedSink)

	public fun deserialize(source: BufferedSource): Config
}

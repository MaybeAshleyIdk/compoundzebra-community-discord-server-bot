package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import okio.BufferedSink
import okio.BufferedSource
import javax.annotation.CheckReturnValue

public interface ConfigSerializer {

	public fun serialize(config: Config, sink: BufferedSink)

	@CheckReturnValue
	public fun deserialize(source: BufferedSource): Config
}

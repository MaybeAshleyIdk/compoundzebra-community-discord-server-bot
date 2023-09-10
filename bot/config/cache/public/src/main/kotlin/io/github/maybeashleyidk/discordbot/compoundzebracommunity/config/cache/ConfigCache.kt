package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import javax.annotation.CheckReturnValue

public interface ConfigCache {

	@CheckReturnValue
	public fun getValue(): Config
}

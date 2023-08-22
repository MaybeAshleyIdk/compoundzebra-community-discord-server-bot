package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import javax.annotation.CheckReturnValue

public abstract class ConfigLoader {

	@CheckReturnValue
	public abstract fun load(): Config
}

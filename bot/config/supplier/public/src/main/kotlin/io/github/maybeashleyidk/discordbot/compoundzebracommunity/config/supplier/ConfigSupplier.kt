package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import javax.annotation.CheckReturnValue

public interface ConfigSupplier {

	@CheckReturnValue
	public fun get(): Config
}

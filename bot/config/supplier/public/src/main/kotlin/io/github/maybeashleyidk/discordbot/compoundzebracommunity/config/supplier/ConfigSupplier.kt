package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config

public interface ConfigSupplier {

	public fun get(): Config
}

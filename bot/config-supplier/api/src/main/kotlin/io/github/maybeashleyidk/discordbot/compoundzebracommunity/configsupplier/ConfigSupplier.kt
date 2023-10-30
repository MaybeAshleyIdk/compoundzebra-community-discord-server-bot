package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config

public interface ConfigSupplier {

	public fun get(): Config
}

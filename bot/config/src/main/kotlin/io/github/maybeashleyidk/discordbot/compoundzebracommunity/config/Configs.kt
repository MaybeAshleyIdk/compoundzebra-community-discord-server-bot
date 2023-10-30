package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories.createFallbackConfig
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories.createInitialConfig

public object Configs {

	public fun createFallback(botEnvironmentType: BotEnvironmentType): Config {
		return createFallbackConfig(botEnvironmentType)
	}

	public fun createInitial(botEnvironmentType: BotEnvironmentType): Config {
		return createInitialConfig(botEnvironmentType)
	}
}

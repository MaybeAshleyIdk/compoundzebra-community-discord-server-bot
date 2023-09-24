package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotEnvironmentType

public object Configs {

	public fun createFallback(botEnvironmentType: BotEnvironmentType): Config {
		return createFallbackConfig(botEnvironmentType)
	}

	public fun createInitial(botEnvironmentType: BotEnvironmentType): Config {
		return createInitialConfig(botEnvironmentType)
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.buildtype.BotBuildType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories.createFallbackConfig
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.factories.createInitialConfig

public object Configs {

	public fun createFallback(botBuildType: BotBuildType): Config {
		return createFallbackConfig(botBuildType)
	}

	public fun createInitial(botBuildType: BotBuildType): Config {
		return createInitialConfig(botBuildType)
	}
}

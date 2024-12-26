package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.buildtype.BotBuildType

internal val BUILD_TYPE: BotBuildType = BuildConstants.BUILD_TYPE_NAME.toBuildType()

private fun String.toBuildType(): BotBuildType {
	return when (this) {
		"development" -> BotBuildType.DEVELOPMENT
		"release" -> BotBuildType.RELEASE
		else -> error("Invalid build type \"$this\"")
	}
}

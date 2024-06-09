package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl

import org.gradle.api.initialization.Settings

public object IncludeDslCreator {

	public fun createForTopLevel(settings: Settings): IncludeDsl {
		return IncludeDslImpl.createForTopLevel(settings)
	}
}

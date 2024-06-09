package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.plugin

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl.IncludeDsl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl.IncludeDslCreator
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create

public abstract class IncludeDslExtension(private val includeDsl: IncludeDsl) : IncludeDsl by includeDsl

public class IncludeDslPlugin : Plugin<Settings> {

	override fun apply(settings: Settings) {
		val topLevelIncludeDsl: IncludeDsl = IncludeDslCreator.createForTopLevel(settings)
		settings.extensions.create<IncludeDslExtension>("include", topLevelIncludeDsl)
	}
}

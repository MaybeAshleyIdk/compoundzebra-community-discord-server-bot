package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import org.gradle.api.Plugin
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin

internal inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

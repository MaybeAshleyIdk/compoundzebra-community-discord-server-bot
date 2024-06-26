package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.ConventionsPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

public fun Project.applyConventionsToAllProjects() {
	this.allprojects {
		this@allprojects.apply<ConventionsPlugin>()
	}
}

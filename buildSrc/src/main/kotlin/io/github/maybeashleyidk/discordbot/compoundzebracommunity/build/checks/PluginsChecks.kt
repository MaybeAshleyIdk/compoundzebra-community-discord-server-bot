package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks

import org.gradle.api.Project

private const val PLUGIN_ID_JAVA_APPLICATION: String = "application"
private const val PLUGIN_ID_JAVA_LIBRARY: String = "java-library"
private const val PLUGIN_ID_KOTLIN_JVM: String = "org.jetbrains.kotlin.jvm"

internal fun checkOnlyJavaLibraryPlugins(project: Project) {
	check(project.pluginManager.hasPlugin(PLUGIN_ID_JAVA_LIBRARY)) {
		"Project must have the Java library plugin applied"
	}

	check(!(project.pluginManager.hasPlugin(PLUGIN_ID_JAVA_APPLICATION))) {
		"Project must not have the Java application plugin applied"
	}

	check(!(project.pluginManager.hasPlugin(PLUGIN_ID_KOTLIN_JVM))) {
		"Project must not have the Kotlin JVM plugin applied"
	}
}

internal fun checkNoSourceCodePlugins(project: Project) {
	check(!(project.pluginManager.hasPlugin(PLUGIN_ID_JAVA_APPLICATION))) {
		"Project must not have the Java application plugin applied"
	}

	check(!(project.pluginManager.hasPlugin(PLUGIN_ID_JAVA_LIBRARY))) {
		"Project must not have the Java library plugin applied"
	}

	check(!(project.pluginManager.hasPlugin(PLUGIN_ID_KOTLIN_JVM))) {
		"Project must not have the Kotlin JVM plugin applied"
	}
}

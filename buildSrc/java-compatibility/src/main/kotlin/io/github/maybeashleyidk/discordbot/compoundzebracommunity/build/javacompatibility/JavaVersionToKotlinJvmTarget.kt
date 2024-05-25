package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.javacompatibility

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal fun JavaVersion.toKotlinJvmTarget(): JvmTarget {
	val javaVersionName: String = this.toString()

	val kotlinJvmTarget: JvmTarget? = JvmTarget.values()
		.firstOrNull { jvmTarget: JvmTarget ->
			jvmTarget.target == javaVersionName
		}

	checkNotNull(kotlinJvmTarget) {
		"The Java version $javaVersionName is not a Kotlin JVM target"
	}

	return kotlinJvmTarget
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

internal class JavaCompatibilityPlugin : Plugin<Project> {

	// See <https://jakewharton.com/gradle-toolchains-are-rarely-a-good-idea/> as to why no toolchains are configured.

	override fun apply(project: Project) {
		this.checkIsOneJavaPluginAppliedIn(project)
		this.configureJavaIn(project)
		this.configureKotlinIn(project)
	}

	private fun configureJavaIn(project: Project) {
		project.extensions.configure<JavaPluginExtension> {
			this@configure.targetCompatibility = JavaVersion.VERSION_21
			this@configure.sourceCompatibility = this@configure.targetCompatibility
		}
	}

	private fun configureKotlinIn(project: Project) {
		project.plugins.withType<KotlinPluginWrapper> {
			project.extensions.configure<KotlinJvmProjectExtension> {
				this@configure.compilerOptions {
					val jvmTargetProvider: Provider<JvmTarget> =
						project.provideKotlinJvmTargetFromJavaTargetCompatibility()

					this@compilerOptions.jvmTarget.set(jvmTargetProvider)
				}
			}
		}
	}

	private fun checkIsOneJavaPluginAppliedIn(project: Project) {
		val hasApplicationPlugin: Boolean = project.plugins.hasPlugin(ApplicationPlugin::class)
		val hasLibraryPlugin: Boolean = project.plugins.hasPlugin(JavaLibraryPlugin::class)

		check(!(hasApplicationPlugin && hasLibraryPlugin)) {
			"Must not have both the Java application plugin and the Java library plugin applied"
		}

		check(hasApplicationPlugin || hasLibraryPlugin) {
			"Either the Java application plugin or the Java library plugin must be applied"
		}
	}
}

private fun Project.provideKotlinJvmTargetFromJavaTargetCompatibility(): Provider<JvmTarget> {
	return this
		.provider {
			val javaPluginExtension: JavaPluginExtension = this.extensions.getByType<JavaPluginExtension>()

			javaPluginExtension.targetCompatibility.toKotlinJvmTarget()
		}
}

private fun JavaVersion.toKotlinJvmTarget(): JvmTarget {
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

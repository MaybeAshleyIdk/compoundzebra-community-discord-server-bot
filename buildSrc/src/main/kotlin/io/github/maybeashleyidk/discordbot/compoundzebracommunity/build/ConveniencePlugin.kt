package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

public class ConveniencePlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.plugins.withType<ApplicationPlugin> {
			project.apply<JavaCompatibilityPlugin>()
		}

		project.plugins.withType<JavaLibraryPlugin> {
			project.apply<JavaCompatibilityPlugin>()

			project.plugins.withType<KotlinPluginWrapper> {
				project.extensions.configure<KotlinTopLevelExtension> {
					this@configure.explicitApi()
				}
			}
		}

		project.plugins.withType<KotlinPluginWrapper> {
			val hasJavaApplicationPlugin: Boolean = project.plugins.hasPlugin<ApplicationPlugin>()
			val hasJavaLibraryPlugin: Boolean = project.plugins.hasPlugin<JavaLibraryPlugin>()

			check(hasJavaApplicationPlugin || hasJavaLibraryPlugin) {
				"Before the Kotlin plugin is applied, " +
					"either the Java application plugin or Java library plugin must be applied"
			}
		}

		project.afterEvaluate {
			val hasJavaApplicationPlugin: Boolean = this@afterEvaluate.plugins.hasPlugin<ApplicationPlugin>()
			val hasJavaLibraryPlugin: Boolean = this@afterEvaluate.plugins.hasPlugin<JavaLibraryPlugin>()

			check(!(hasJavaApplicationPlugin && hasJavaLibraryPlugin)) {
				"Must not have both the Java application plugin and the Java library plugin applied"
			}
		}
	}
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

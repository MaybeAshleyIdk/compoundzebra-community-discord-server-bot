package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.utils.asIterable
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.utils.requireLibrary
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaLauncher
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.register

// <https://jakewharton.com/build-on-latest-java-test-through-lowest-java/>

internal class TestingConventionsPlugin : Plugin<Project> {

	private companion object {

		val HIGHEST_JAVA_VERSION_TO_TEST: JavaVersion = JavaVersion.VERSION_23
	}

	override fun apply(project: Project) {
		this.checkIsOneJavaPluginAppliedIn(project)
		this.setUpJUnitDependenciesIn(project)
		this.setUpTasksIn(project)
	}

	private fun setUpJUnitDependenciesIn(project: Project) {
		val versionCatalogProvider: Provider<VersionCatalog> = project.provideLibsVersionCatalog()

		project.configurations.named("testImplementation") {
			this@named.dependencies.addLater(versionCatalogProvider.requireLibrary(alias = "junit-jupiter"))
		}

		project.configurations.named("testRuntimeOnly") {
			this@named.dependencies.addLater(versionCatalogProvider.requireLibrary(alias = "junit-platformLauncher"))
		}
	}

	private fun setUpTasksIn(project: Project) {
		val testTasks: List<TaskProvider<Test>> = this.registerTestTasksIn(project)

		project.tasks.named("test") {
			this@named.dependsOn(*(testTasks.toTypedArray()))
			this@named.enabled = false
		}

		project.tasks.named("check") {
			this@named.dependsOn(*(testTasks.toTypedArray()))
		}
	}

	private fun registerTestTasksIn(project: Project): List<TaskProvider<Test>> {
		val javaPluginExtension: JavaPluginExtension = project.extensions.getByType<JavaPluginExtension>()

		return (javaPluginExtension.targetCompatibility..Companion.HIGHEST_JAVA_VERSION_TO_TEST)
			.asIterable()
			.map { javaVersion: JavaVersion ->
				this.registerTestTask(project.tasks, javaVersion)
			}
	}

	private fun registerTestTask(taskContainer: TaskContainer, javaVersion: JavaVersion): TaskProvider<Test> {
		return taskContainer.register<Test>(name = "testJava${javaVersion.majorVersion}") {
			val testTask: Test = this@register.project.tasks.getByName<Test>("test")

			this@register.description = "Runs the test suite with a Java $javaVersion runtime environment."
			(this@register as Task).group = testTask.group

			this@register.testClassesDirs = testTask.testClassesDirs
			this@register.classpath = testTask.classpath

			this@register.javaLauncher.set(this@register.project.provideJavaToolchainLauncherFor(javaVersion))

			this@register.useJUnitPlatform()
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

private fun Project.provideJavaToolchainLauncherFor(javaVersion: JavaVersion): Provider<JavaLauncher> {
	return this
		.provider {
			this.extensions.getByName<JavaToolchainService>(name = "javaToolchains")
		}
		.flatMap { javaToolchains: JavaToolchainService ->
			javaToolchains.launcherFor {
				this@launcherFor.languageVersion.set(JavaLanguageVersion.of(javaVersion.majorVersion))
			}
		}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectPluginsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.requireType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.selectPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.flattenDepth
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.hasPlugin

internal object ProjectPluginsEnforcement {

	fun enforceProjectsPlugins(projectTree: Tree<Project>) {
		for (project: Project in projectTree.flattenDepth()) {
			this.enforceProjectPluginsFor(project)
		}
	}

	private fun enforceProjectPluginsFor(project: Project) {
		val pluginsPolicy: ProjectPluginsPolicy = project.requireType().selectPolicy().pluginsPolicy

		when (pluginsPolicy) {
			ProjectPluginsPolicy.JAVA_APPLICATION_OR_LIBRARY_WITH_KTJVM -> {
				this.enforceApplicationOrLibraryWithKtJvm(project)
			}

			ProjectPluginsPolicy.JAVA_LIBRARY_WITH_KTJVM -> this.enforceLibraryWithKtJvm(project)
			ProjectPluginsPolicy.JAVA_LIBRARY_WITHOUT_KTJVM -> this.enforceLibraryWithoutKtJvm(project)
			ProjectPluginsPolicy.NONE -> this.enforceNone(project)
		}
	}

	private fun enforceApplicationOrLibraryWithKtJvm(project: Project) {
		check(project.hasJavaApplicationPlugin() || project.hasJavaLibraryPlugin()) {
			"The $project must either have the Java application- or library plugin applied to it"
		}

		check(!(project.hasJavaApplicationPlugin() && project.hasJavaLibraryPlugin())) {
			"The $project must not have both the Java application- and library plugin applied to it"
		}

		check(project.hasKotlinJvmPlugin()) {
			"The $project must have the Kotlin JVM plugin applied to it"
		}
	}

	private fun enforceLibraryWithKtJvm(project: Project) {
		check(project.hasJavaLibraryPlugin()) {
			"The $project must have the Java library plugin applied to it"
		}

		check(!(project.hasJavaApplicationPlugin())) {
			"The $project must not have the Java application plugin applied to it"
		}

		check(project.hasKotlinJvmPlugin()) {
			"The $project must have the Kotlin JVM plugin applied to it"
		}
	}

	private fun enforceLibraryWithoutKtJvm(project: Project) {
		check(project.hasJavaLibraryPlugin()) {
			"The $project must have the Java library plugin applied to it"
		}

		check(!(project.hasJavaApplicationPlugin())) {
			"The $project must not have the Java application plugin applied to it"
		}

		check(!(project.hasKotlinJvmPlugin())) {
			"The $project must not have the Kotlin JVM plugin applied to it"
		}
	}

	private fun enforceNone(project: Project) {
		check(!(project.hasJavaLibraryPlugin())) {
			"The $project must not have the Java library plugin applied to it"
		}

		check(!(project.hasJavaApplicationPlugin())) {
			"The $project must not have the Java application plugin applied to it"
		}

		check(!(project.hasKotlinJvmPlugin())) {
			"The $project must not have the Kotlin JVM plugin applied to it"
		}
	}
}

private fun Project.hasJavaApplicationPlugin(): Boolean {
	return (this.plugins.hasPlugin(ApplicationPlugin::class) || this.pluginManager.hasPlugin("application"))
}

private fun Project.hasJavaLibraryPlugin(): Boolean {
	return (this.plugins.hasPlugin(JavaLibraryPlugin::class) || this.pluginManager.hasPlugin("java-library"))
}

private fun Project.hasKotlinJvmPlugin(): Boolean {
	return this.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")
}

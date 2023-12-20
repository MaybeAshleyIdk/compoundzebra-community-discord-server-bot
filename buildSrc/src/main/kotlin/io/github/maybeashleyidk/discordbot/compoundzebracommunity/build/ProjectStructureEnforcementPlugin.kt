package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins.getProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isComposite
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isNamespace
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isNotMaybeWiring
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isRegularStandalone
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceImplementation
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceInterface
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceWiring
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isStandalone
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class ProjectStructureEnforcementPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.evaluationDependsOnChildren()
		target.afterEvaluate {
			this@ProjectStructureEnforcementPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkProjectName(project)
		this.checkProjectPlugins(project)
		this.checkProjectDependencies(project)
		this.checkProjectChildren(project)
	}

	private fun checkProjectName(project: Project) {
		val projectType: ProjectType = project.getProjectType()

		if (projectType.isServiceInterface()) {
			check(project.name == "api") {
				"Project name must be \"api\""
			}
		} else {
			check(project.name != "api") {
				"Project name must not be \"api\""
			}
		}

		if (projectType.isServiceImplementation()) {
			check(project.name.matches(Regex("^impl(-.+)?$"))) {
				"Project name must match the regular expression /^impl(-.+)?\$/"
			}
		} else {
			check(!(project.name.startsWith("impl"))) {
				"Project name must not start with \"impl\""
			}
		}

		if (projectType.isServiceWiring()) {
			check(project.name == "wiring") {
				"Project name must be \"wiring\""
			}
		}
		if (projectType.isNotMaybeWiring()) {
			check(project.name != "wiring") {
				"Project name must not be \"wiring\""
			}
		}
	}

	private fun checkProjectPlugins(project: Project) {
		val projectType: ProjectType = project.getProjectType()

		if (projectType.isStandalone()) {
			if (projectType.isRegularStandalone()) {
				val hasJavaLibraryPlugin: Boolean = project.pluginManager.hasPlugin("java-library")
				check(hasJavaLibraryPlugin || project.pluginManager.hasPlugin("application")) {
					"Project must have either the Java library or Java application plugin applied"
				}
			} else {
				check(project.pluginManager.hasPlugin("java-library")) {
					"Project must have the Java library plugin applied"
				}

				check(!(project.pluginManager.hasPlugin("application"))) {
					"Project must not have the Java application plugin applied"
				}
			}

			check(project.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")) {
				"Project must have the Kotlin JVM plugin applied"
			}
		}

		if (projectType.isComposite()) {
			check(project.pluginManager.hasPlugin("java-library")) {
				"Project must have the Java library plugin applied"
			}

			check(!(project.pluginManager.hasPlugin("application"))) {
				"Project must not have the Java application plugin applied"
			}

			check(!(project.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm"))) {
				"Project must not have the Kotlin JVM plugin applied"
			}
		}

		if (projectType.isNamespace()) {
			check(!(project.pluginManager.hasPlugin("application"))) {
				"Project must not have the Java application plugin applied"
			}

			check(!(project.pluginManager.hasPlugin("java-library"))) {
				"Project must not have the Java library plugin applied"
			}

			check(!(project.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm"))) {
				"Project must not have the Kotlin JVM plugin applied"
			}
		}
	}

	private fun checkProjectDependencies(project: Project) {
		val projectType: ProjectType = project.getProjectType()

		// TODO
	}

	private fun checkProjectChildren(project: Project) {
		val projectType: ProjectType = project.getProjectType()

		if (projectType.isStandalone()) {
			check(project.childProjects.isEmpty()) {
				"Project must not have any child projects (${project.childProjects.values.joinToString()})"
			}
		}

		// TODO
	}
}

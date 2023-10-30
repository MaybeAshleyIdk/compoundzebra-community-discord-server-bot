package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as a "group" project.
 *
 * @see StandaloneProjectPlugin
 */
public class GroupProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.evaluationDependsOnChildren()

		project.afterEvaluate {
			this@GroupProjectPlugin.checkProject(project = this@afterEvaluate)
			this@GroupProjectPlugin.addChildProjectsAsDependencies(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
	}

	private fun checkName(project: Project) {
		check(project.name.isLegalProjectName()) {
			"The name of the group $project is illegal"
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The group $project has the standalone project plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isNotEmpty()) {
			"The group $project has no child projects, but it must at least one"
		}

		for (childProject: Project in project.childProjects.values) {
			check(childProject.plugins.hasPlugin<StandaloneProjectPlugin>()) {
				"The child $childProject of the parent group $project doesn't have " +
					"the standalone project plugin applied to it, but it must have"
			}
		}
	}

	private fun addChildProjectsAsDependencies(project: Project) {
		project.configurations.named("api") {
			project.childProjects
				.map { (_: String, childProject: Project) ->
					project.dependencies.project(childProject.path)
				}
				.forEach(this@named.dependencies::add)
		}
	}
}

private fun String.isLegalProjectName(): Boolean {
	return (this != "api") && (this != "impl") && (this != "wiring") && !(this.startsWith("impl-"))
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

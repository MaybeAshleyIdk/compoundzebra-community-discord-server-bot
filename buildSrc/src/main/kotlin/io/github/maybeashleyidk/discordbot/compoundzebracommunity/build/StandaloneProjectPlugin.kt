package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ApiImplWiringProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ApiProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ImplProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.WiringProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as a "standalone" project.
 */
public class StandaloneProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.afterEvaluate {
			this@StandaloneProjectPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
	}

	private fun checkName(project: Project) {
		check(project.name.isLegalProjectName()) {
			"The name of the standalone $project is illegal"
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<ApiImplWiringProjectPlugin> {
			val msg: String = "The standalone $project has the API-implementation-wiring project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The standalone $project has the API project plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			error("The standalone $project has the implementation project plugin applied to it, but it must not")
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The standalone $project has the wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The standalone $project has the group project plugin applied to it, but it must not")
		}

		project.plugins.withType<WiringAssimilationProjectPlugin> {
			error("The standalone $project has the wiring-assimilation project plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isEmpty()) {
			"The standalone $project has ${project.childProjects.size} child project(s)" +
				", but it must not have any"
		}
	}
}

private fun String.isLegalProjectName(): Boolean {
	return (this != "api") && (this != "impl") && (this != "wiring") && !(this.startsWith("impl-"))
}

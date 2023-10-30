package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.GroupProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.StandaloneProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.WiringAssimilationProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as an "API" project.
 *
 * @see ApiImplWiringProjectPlugin
 */
public class ApiProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.afterEvaluate {
			this@ApiProjectPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
		this.checkParentProject(project)
	}

	private fun checkName(project: Project) {
		check(project.name == "api") {
			"The name of the API $project must be \"api\""
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The API $project has the standalone project plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiImplWiringProjectPlugin> {
			error("The API $project has the API-implementation-wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			error("The API $project has the implementation project plugin applied to it, but it must not")
		}

		project.plugins.withType<InternalWiringProjectPlugin> {
			val msg: String = "The API $project has the internal-wiring project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The API $project has the wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The API $project has the group project plugin applied to it, but it must not")
		}

		project.plugins.withType<WiringAssimilationProjectPlugin> {
			error("The API $project has the wiring-assimilation project plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isEmpty()) {
			"The API $project has ${project.childProjects.size} child project(s), but it must not have any"
		}
	}

	private fun checkParentProject(project: Project) {
		val parentProject: Project =
			checkNotNull(project.parent) {
				"The API $project has no parent project, but it must have one (i.e.: it must not be a root project)"
			}

		parentProject.afterEvaluate {
			check(this@afterEvaluate.plugins.hasPlugin<ApiImplWiringProjectPlugin>()) {
				"The parent ${this@afterEvaluate} of the API $project does not have " +
					"the API-implementation-wiring project plugin applied, but it must have"
			}
		}
	}
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.GroupProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.PartitionedProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.StandaloneProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.WiringAssimilationProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as an "internal-wiring" project.
 *
 * @see ImplProjectPlugin
 */
public class InternalWiringProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.afterEvaluate {
			this@InternalWiringProjectPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
		this.checkParentProject(project)
	}

	private fun checkName(project: Project) {
		check(project.name == "wiring") {
			"The name of the internal-wiring $project must be \"wiring\""
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The internal-wiring $project has the standalone project plugin applied to it, but it must not")
		}

		project.plugins.withType<PartitionedProjectPlugin> {
			error("The internal-wiring $project has the partitioned project plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiImplWiringProjectPlugin> {
			val msg: String =
				"The internal-wiring $project has the API-implementation-wiring project plugin applied to it" +
					", but it must not"
			error(msg)
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The internal-wiring $project has the API project plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			val msg: String = "The internal-wiring $project has the implementation project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The internal-wiring $project has the wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The internal-wiring $project has the group project plugin applied to it, but it must not")
		}

		project.plugins.withType<WiringAssimilationProjectPlugin> {
			val msg: String = "The internal-wiring $project has the wiring-assimilation project plugin applied to it" +
				", but it must not"
			error(msg)
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isEmpty()) {
			"The internal-wiring $project has ${project.childProjects.size} child project(s)" +
				", but it must not have any"
		}
	}

	private fun checkParentProject(project: Project) {
		val parentProject: Project =
			checkNotNull(project.parent) {
				"The internal-wiring $project has no parent project" +
					", but it must have one (i.e.: it must not be a root project)"
			}

		parentProject.afterEvaluate {
			check(this@afterEvaluate.plugins.hasPlugin<ImplProjectPlugin>()) {
				"The parent ${this@afterEvaluate} of the internal-wiring $project does not have " +
					"the implementation project plugin applied, but it must have"
			}
		}
	}
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

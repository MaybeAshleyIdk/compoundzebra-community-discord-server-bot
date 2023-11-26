package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ApiImplWiringProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ApiProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ImplProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.WiringProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType
import kotlin.reflect.KClass

/**
 * Plugin that marks the project it is applied to as a "partitioned" project.
 *
 * @see StandaloneProjectPlugin
 * @see ApiImplWiringProjectPlugin
 * @see WiringAssimilationProjectPlugin
 */
public class PartitionedProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.evaluationDependsOnChildren()

		project.afterEvaluate {
			this@PartitionedProjectPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
	}

	private fun checkName(project: Project) {
		check(project.name.isLegalProjectName()) {
			"The name of the partitioned $project is illegal"
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The partitioned $project has the standalone plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiImplWiringProjectPlugin> {
			error("The partitioned $project has the API-implementation-wiring plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The partitioned $project has the API plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			error("The partitioned $project has the implementation plugin applied to it, but it must not")
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The partitioned $project has the wiring plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isNotEmpty()) {
			"The partitioned $project has no child projects, but it must have at least one"
		}

		for (childProject: Project in project.childProjects.values) {
			val hasAllowedProjectTypePlugin: Boolean = childProject.plugins
				.hasAnyPlugin(
					StandaloneProjectPlugin::class,
					ApiImplWiringProjectPlugin::class,
					WiringAssimilationProjectPlugin::class,
				)

			check(hasAllowedProjectTypePlugin) {
				"The child $childProject of partitioned $project does not have any of " +
					"the required project type plugins applied, but it must have"
			}
		}

		val wiringAssimilationChildProjectsCount: Int = project.childProjects.values
			.count { childProject: Project ->
				(childProject.name == "wiring") && childProject.plugins.hasPlugin<WiringAssimilationProjectPlugin>()
			}

		check(wiringAssimilationChildProjectsCount <= 1) {
			"The partitioned $project has $wiringAssimilationChildProjectsCount child projects whose name " +
				"is \"wiring\" and has the wiring-assimilation project plugin applied, but it must have at most one"
		}
	}
}

private fun String.isLegalProjectName(): Boolean {
	return (this != "api") && (this != "impl") && (this != "wiring") && !(this.startsWith("impl-"))
}

private fun PluginContainer.hasAnyPlugin(vararg types: KClass<out Plugin<*>>): Boolean {
	return types.any(this::hasPlugin)
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

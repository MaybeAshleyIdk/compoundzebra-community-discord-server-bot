package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.GroupProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.StandaloneProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType
import kotlin.reflect.KClass

/**
 * Plugin that marks the project it is applied to as an "implementation" project.
 *
 * @see InternalWiringProjectPlugin
 * @see ApiImplWiringProjectPlugin
 */
public class ImplProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.evaluationDependsOnChildren()

		project.afterEvaluate {
			this@ImplProjectPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
		this.checkParentProject(project)
	}

	private fun checkName(project: Project) {
		check(project.name.isLegalProjectName()) {
			"The name of the implementation $project is illegal"
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The implementation $project has the standalone project plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiImplWiringProjectPlugin> {
			val msg: String =
				"The implementation $project has the API-implementation-wiring project plugin applied to it" +
					", but it must not"
			error(msg)
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The implementation $project has the API project plugin applied to it, but it must not")
		}

		project.plugins.withType<InternalWiringProjectPlugin> {
			val msg: String = "The implementation $project has the internal-wiring project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The implementation $project has the wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The implementation $project has the group project plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		for (childProject: Project in project.childProjects.values) {
			val hasAllowedProjectTypePlugin: Boolean = childProject.plugins
				.hasAnyPlugin(
					StandaloneProjectPlugin::class,
					GroupProjectPlugin::class,
					InternalWiringProjectPlugin::class,
				)

			check(hasAllowedProjectTypePlugin) {
				"The child project $childProject of implementation $project does not have any of " +
					"the required project type plugins applied, but it must have"
			}
		}

		val internalWiringChildProjectsCount: Int = project.childProjects.values
			.count { childProject: Project ->
				(childProject.name == "wiring") && childProject.plugins.hasPlugin<InternalWiringProjectPlugin>()
			}

		check(internalWiringChildProjectsCount <= 1) {
			"The implementation $project has $internalWiringChildProjectsCount child projects whose name " +
				"is \"wiring\" and has the internal-wiring project plugin applied, but it must have at most one"
		}
	}

	private fun checkParentProject(project: Project) {
		val parentProject: Project =
			checkNotNull(project.parent) {
				"The implementation $project has no parent project" +
					", but it must have one (i.e.: it must not be a root project)"
			}

		parentProject.afterEvaluate {
			check(this@afterEvaluate.plugins.hasPlugin<ApiImplWiringProjectPlugin>()) {
				"The parent ${this@afterEvaluate} of the implementation $project does not have " +
					"the API-implementation-wiring project plugin applied, but it must have"
			}
		}
	}
}

private fun String.isLegalProjectName(): Boolean {
	return (this == "impl") || (this.startsWith("impl-") && (this.length > 5))
}

private fun PluginContainer.hasAnyPlugin(vararg types: KClass<out Plugin<*>>): Boolean {
	return types.any(this::hasPlugin)
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

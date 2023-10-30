package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.GroupProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.StandaloneProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as a "wiring" project.
 *
 * @see ApiImplWiringProjectPlugin
 */
public class WiringProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.afterEvaluate {
			this@WiringProjectPlugin.checkProject(project = this@afterEvaluate)
			this@WiringProjectPlugin
				.addImplProjectSiblingsInternalWiringChildProjectAsDependency(project = this@afterEvaluate)
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
			"The name of the wiring $project must be \"wiring\""
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The wiring $project has the standalone project plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiImplWiringProjectPlugin> {
			error("The wiring $project has the API-implementation-wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The wiring $project has the API project plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			error("The wiring $project has the implementation project plugin applied to it, but it must not")
		}

		project.plugins.withType<InternalWiringProjectPlugin> {
			val msg: String = "The wiring $project has the internal-wiring project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The wiring $project has the group project plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isEmpty()) {
			"The wiring $project has ${project.childProjects.size} child project(s), but it must not have any"
		}
	}

	private fun checkParentProject(project: Project) {
		val parentProject: Project =
			checkNotNull(project.parent) {
				"The wiring $project has no parent project, but it must have one (i.e.: it must not be a root project)"
			}

		parentProject.afterEvaluate {
			check(this@afterEvaluate.plugins.hasPlugin<ApiImplWiringProjectPlugin>()) {
				"The parent ${this@afterEvaluate} of the wiring $project does not have " +
					"the API-implementation-wiring project plugin applied, but it must have"
			}
		}
	}

	private fun addImplProjectSiblingsInternalWiringChildProjectAsDependency(project: Project) {
		val parentProject: Project =
			checkNotNull(project.parent) {
				"The wiring $project has no parent project, but it must have one (i.e.: it must not be a root project)"
			}

		val implSiblingProjects: List<Project> = parentProject.childProjects.values
			.filter { siblingProject: Project ->
				(siblingProject != project) &&
					siblingProject.name.isLegalImplProjectName() &&
					siblingProject.plugins.hasPlugin<ImplProjectPlugin>()
			}

		val singleImplSiblingProject: Project? = implSiblingProjects.singleOrNull()

		checkNotNull(singleImplSiblingProject) {
			"The wiring $project has ${implSiblingProjects.size} sibling projects whose name " +
				"either is \"impl\" or has the prefix \"impl-\" and has the implementation project plugin applied" +
				", but it must have exactly one"
		}

		val internalWiringNephewProjects: List<Project> = singleImplSiblingProject.childProjects.values
			.filter { nephewProject: Project ->
				(nephewProject.name == "wiring") && nephewProject.plugins.hasPlugin<InternalWiringProjectPlugin>()
			}

		check(internalWiringNephewProjects.size <= 1) {
			"The implementation $singleImplSiblingProject has ${internalWiringNephewProjects.size} child projects " +
				"whose name is \"wiring\" and has the internal-wiring project plugin applied" +
				", but it must have at most one"
		}

		val singleInternalWiringNephewProject: Project = internalWiringNephewProjects.singleOrNull()
			?: return

		project.configurations.named("api") {
			this@named.dependencies.add(project.dependencies.project(singleInternalWiringNephewProject.path))
		}
	}
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

private fun String.isLegalImplProjectName(): Boolean {
	return (this == "impl") || (this.startsWith("impl-") && (this.length > 5))
}

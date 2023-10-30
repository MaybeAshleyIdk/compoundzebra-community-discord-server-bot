package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.GroupProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.StandaloneProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.WiringAssimilationProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as an "API-implementation-wiring" project.
 *
 * @see ApiProjectPlugin
 * @see ImplProjectPlugin
 * @see WiringProjectPlugin
 */
public class ApiImplWiringProjectPlugin : Plugin<Project> {

	private data class ChildProjects(
		val api: Project,
		val impl: Project,
		val wiring: Project,
	)

	override fun apply(project: Project) {
		project.evaluationDependsOnChildren()

		project.afterEvaluate {
			this@ApiImplWiringProjectPlugin.checkProject(project = this@afterEvaluate)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		val childProjects: ChildProjects = this.checkChildProjects(project)
		this.checkDependencies(project, childProjects)
	}

	private fun checkName(project: Project) {
		check(project.name.isLegalProjectName()) {
			"The name of the API-implementation-wiring $project is illegal"
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			val msg: String = "The API-implementation-wiring $project has the standalone project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The API-implementation-wiring $project has the API project plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			val msg: String =
				"The API-implementation-wiring $project has the implementation project plugin applied to it" +
					", but it must not"
			error(msg)
		}

		project.plugins.withType<InternalWiringProjectPlugin> {
			val msg: String =
				"The API-implementation-wiring $project has the internal-wiring project plugin applied to it" +
					", but it must not"
			error(msg)
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The API-implementation-wiring $project has the wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The API-implementation-wiring $project has the group project plugin applied to it, but it must not")
		}

		project.plugins.withType<WiringAssimilationProjectPlugin> {
			val msg: String =
				"The API-implementation-wiring $project has the wiring-assimilation project plugin applied to it" +
					", but it must not"
			error(msg)
		}
	}

	// region checking child projects

	private fun checkChildProjects(project: Project): ChildProjects {
		check(project.childProjects.size == 3) {
			"The The API-implementation-wiring $project has ${project.childProjects.size} child project(s)" +
				", but it must have exactly 3"
		}

		val apiChildProject: Project = this.checkApiChildProject(project)
		val implChildProject: Project = this.checkImplChildProject(project)
		val wiringChildProject: Project = this.checkWiringChildProject(project)

		return ChildProjects(
			api = apiChildProject,
			impl = implChildProject,
			wiring = wiringChildProject,
		)
	}

	private fun checkApiChildProject(project: Project): Project {
		val apiChildProject: Project? = project.childProjects.values
			.singleOrNull { childProject: Project ->
				(childProject.name == "api") && childProject.plugins.hasPlugin<ApiProjectPlugin>()
			}

		checkNotNull(apiChildProject) {
			"The API-implementation-wiring $project has no child project that has the the name \"api\" and " +
				"the API project plugin applied, but it must have one"
		}

		return apiChildProject
	}

	private fun checkImplChildProject(project: Project): Project {
		val implChildProjects: List<Project> = project.childProjects.values
			.filter { childProject: Project ->
				childProject.name.isLegalImplProjectName() && childProject.plugins.hasPlugin<ImplProjectPlugin>()
			}

		val singleImplChildProject: Project? = implChildProjects.singleOrNull()

		checkNotNull(singleImplChildProject) {
			"The API-implementation-wiring $project has ${implChildProjects.size} child projects whose name " +
				"either is \"impl\" or has the prefix \"impl-\" and has the implementation project plugin applied" +
				", but it must have exactly one"
		}

		return singleImplChildProject
	}

	private fun checkWiringChildProject(project: Project): Project {
		val wiringChildProject: Project? = project.childProjects.values
			.singleOrNull { childProject: Project ->
				(childProject.name == "wiring") && childProject.plugins.hasPlugin<WiringProjectPlugin>()
			}

		checkNotNull(wiringChildProject) {
			"The API-implementation-wiring $project has no child project that has both the name \"wiring\" and " +
				"the API project plugin applied, but it must have one"
		}

		return wiringChildProject
	}

	// endregion

	// region check dependencies

	private fun checkDependencies(project: Project, childProjects: ChildProjects) {
		this.checkApiChildProjectDependency(project, childProjects.api)
		this.checkImplChildProjectDependency(project, childProjects.impl)
		this.checkWiringChildProjectDependency(project, childProjects.wiring)
	}

	private fun checkApiChildProjectDependency(project: Project, apiChildProject: Project) {
		val apiChildProjectDependency: ProjectDependency = project.dependencies.project(apiChildProject.path)

		project.configurations.named("api") {
			check(this@named.dependencies.contains(apiChildProjectDependency)) {
				"The API-implementation-wiring $project has no API dependency on the child $apiChildProject" +
					", but it must have"
			}
		}
	}

	private fun checkImplChildProjectDependency(project: Project, implChildProject: Project) {
		val implChildProjectDependency: ProjectDependency = project.dependencies.project(implChildProject.path)

		project.configurations.named("implementation") {
			check(!(this@named.dependencies.contains(implChildProjectDependency))) {
				"The API-implementation-wiring $project has a dependency on the child $implChildProject" +
					", but it mustn't have"
			}
		}

		project.configurations.named("api") {
			check(!(this@named.dependencies.contains(implChildProjectDependency))) {
				"The API-implementation-wiring $project has a dependency on the child $implChildProject" +
					", but it mustn't have"
			}
		}
	}

	private fun checkWiringChildProjectDependency(project: Project, wiringChildProject: Project) {
		val wiringChildProjectDependency: ProjectDependency = project.dependencies.project(wiringChildProject.path)

		project.configurations.named("implementation") {
			check(!(this@named.dependencies.contains(wiringChildProjectDependency))) {
				"The API-implementation-wiring $project has a dependency on the child $wiringChildProject" +
					", but it mustn't have"
			}
		}

		project.configurations.named("api") {
			check(!(this@named.dependencies.contains(wiringChildProjectDependency))) {
				"The API-implementation-wiring $project has a dependency on the child $wiringChildProject" +
					", but it mustn't have"
			}
		}
	}

	// endregion
}

private fun String.isLegalProjectName(): Boolean {
	return (this != "api") && (this != "impl") && (this != "wiring") && !(this.startsWith("impl-"))
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

private fun String.isLegalImplProjectName(): Boolean {
	return (this == "impl") || (this.startsWith("impl-") && (this.length > 5))
}

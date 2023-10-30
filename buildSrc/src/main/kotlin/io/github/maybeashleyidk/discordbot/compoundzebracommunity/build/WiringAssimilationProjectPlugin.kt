package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ApiImplWiringProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ApiProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.ImplProjectPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.apiimplwiring.WiringProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType

/**
 * Plugin that marks the project it is applied to as a "wiring-assimilation" project.
 *
 * All sibling projects, and the sibling's children- and grandchildren projects (recursively), that are marked as
 * "wiring" projects by the plugin [WiringProjectPlugin] are added to
 * the configuration `implementation` as dependencies.
 */
public class WiringAssimilationProjectPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		val wiringProjectCandidates: List<Project> = this.getWiringProjectCandidates(project)

		for (wiringProjectCandidate: Project in wiringProjectCandidates) {
			project.evaluationDependsOn(wiringProjectCandidate.path)
		}

		project.afterEvaluate {
			this@WiringAssimilationProjectPlugin.checkProject(project = this@afterEvaluate)
			this@WiringAssimilationProjectPlugin
				.addWiringProjectsAsDependencies(
					project = this@afterEvaluate,
					projectCandidates = wiringProjectCandidates,
				)
		}
	}

	private fun checkProject(project: Project) {
		this.checkName(project)
		this.checkPlugins(project)
		this.checkChildProjects(project)
	}

	private fun checkName(project: Project) {
		check(project.name == "wiring") {
			"The name of the wiring-assimilation $project must be \"wiring\""
		}
	}

	private fun checkPlugins(project: Project) {
		project.plugins.withType<StandaloneProjectPlugin> {
			error("The wiring-assimilation $project has the standalone project plugin applied to it, but it must not")
		}

		project.plugins.withType<ApiImplWiringProjectPlugin> {
			val msg: String =
				"The wiring-assimilation $project has the API-implementation-wiring project plugin applied to it" +
					", but it must not"
			error(msg)
		}

		project.plugins.withType<ApiProjectPlugin> {
			error("The wiring-assimilation $project has the API project plugin applied to it, but it must not")
		}

		project.plugins.withType<ImplProjectPlugin> {
			val msg: String = "The wiring-assimilation $project has the implementation project plugin applied to it" +
				", but it must not"
			error(msg)
		}

		project.plugins.withType<WiringProjectPlugin> {
			error("The wiring-assimilation $project has the wiring project plugin applied to it, but it must not")
		}

		project.plugins.withType<GroupProjectPlugin> {
			error("The wiring-assimilation $project has the group project plugin applied to it, but it must not")
		}
	}

	private fun checkChildProjects(project: Project) {
		check(project.childProjects.isEmpty()) {
			"The wiring-assimilation $project has ${project.childProjects.size} child project(s)" +
				", but it must not have any"
		}
	}

	private fun addWiringProjectsAsDependencies(project: Project, projectCandidates: List<Project>) {
		val wiringProjects: List<Project> = projectCandidates
			.filter { projectCandidate: Project ->
				projectCandidate.plugins.hasPlugin<WiringProjectPlugin>()
			}

		project.configurations.named("implementation") {
			for (wiringProject: Project in wiringProjects) {
				this@named.dependencies.add(project.dependencies.project(wiringProject.path))
			}
		}
	}

	private fun getWiringProjectCandidates(project: Project): List<Project> {
		val parentProject: Project =
			checkNotNull(project.parent) {
				"The wiring-assimilation $project has no parent project" +
					", but it must have one (i.e.: it must not be a root project)"
			}

		return parentProject.childProjects.values
			.flatMap { siblingProject: Project ->
				if (siblingProject == project) {
					return@flatMap emptyList()
				}

				siblingProject.childProjects.values
					.filter { nephewProject: Project ->
						nephewProject.name == "wiring"
					}
			}
	}
}

private inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this.hasPlugin(T::class)
}

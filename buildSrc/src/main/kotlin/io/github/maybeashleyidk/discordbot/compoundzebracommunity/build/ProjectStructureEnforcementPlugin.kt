package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks.checkProjectHasNoDependencies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks.checkProjectHasNoNamespaceProjectDependencies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks.checkProjectHasNoParentCompositeProjectDependencies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks.checkProjectHasNoServiceImplementationProjectDependencies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks.checkProjectHasNoServiceInterfaceProjectDependencies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks.checkProjectHasNoServiceWiringProjectDependencies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins.getProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins.getProjectTypeOrNull
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isBranch
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isComposite
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isNamespace
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isNotMaybeWiring
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isRegularStandalone
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceChild
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceImplementation
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceInterface
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceWiring
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isStandalone
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ProjectDependency

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

		checkProjectHasNoParentCompositeProjectDependencies(project)

		checkProjectHasNoNamespaceProjectDependencies(project)

		if (projectType.isStandalone()) {
			// TODO: no dependencies on composite (grand)child projects (if that composite project isn't our parent)
		}

		if (projectType.isRegularStandalone()) {
			checkProjectHasNoServiceInterfaceProjectDependencies(project)
			checkProjectHasNoServiceImplementationProjectDependencies(project)
			if (project.name != "wiring") {
				checkProjectHasNoServiceWiringProjectDependencies(project)
			}
		}

		if (projectType.isServiceInterface()) {
			checkProjectHasNoServiceInterfaceProjectDependencies(project)
			checkProjectHasNoServiceImplementationProjectDependencies(project)
			checkProjectHasNoServiceWiringProjectDependencies(project)
		}

		if (projectType.isServiceImplementation()) {
			// TODO: no dependencies on service interface (other than the sibling), service implementation or
			//       service wiring
			checkProjectHasNoServiceImplementationProjectDependencies(project)
			checkProjectHasNoServiceWiringProjectDependencies(project)
		}

		if (projectType.isServiceWiring()) {
			checkProjectHasNoServiceInterfaceProjectDependencies(project)
			checkProjectHasNoServiceWiringProjectDependencies(project)

			val siblingServiceImplementationProjects: List<Project> = project.getSiblingProjects()
				.filter { siblingProject: Project ->
					siblingProject.getProjectTypeOrNull()?.isServiceImplementation() ?: false
				}

			val projectDependencies: List<Project> = project.configurations
				.flatMap(Configuration::getDependencies)
				.filterIsInstance<ProjectDependency>()
				.map(ProjectDependency::getDependencyProject)

			val hasAllSiblingServiceImplementationProjectsAsDependency: Boolean = siblingServiceImplementationProjects
				.all { siblingServiceImplementationProject: Project ->
					siblingServiceImplementationProject in projectDependencies
				}

			check(hasAllSiblingServiceImplementationProjectsAsDependency) {
				// TODO
			}

			val hasOnlyProjectDependenciesOnSiblingServiceImplementationProjects: Boolean = projectDependencies
				.all { dependency: Project ->
					dependency in siblingServiceImplementationProjects
				}

			check(hasOnlyProjectDependenciesOnSiblingServiceImplementationProjects) {
				// TODO
			}
		}

		if (projectType.isComposite()) {
			val apiDependencies: DependencySet = project.configurations
				.getByName("api")
				.dependencies

			check(apiDependencies.isNotEmpty()) {
				"Project must have at least one dependency in the configuration \"api\""
			}

			val y = apiDependencies.all { x ->
				x is ProjectDependency
			}
			check(y) {
				"Project's dependencies in the configuration \"api\" must be a project dependencies"
			}

			val z = apiDependencies
				.all { x ->
					(x as ProjectDependency).dependencyProject in project.childProjects.values
				}

			check(z) {
				"Project's dependencies in the configuration \"api\" must be child project dependencies"
			}

			// TODO: if project is service, check if apiDependencies has exactly one and that one is service interface

			val hasOtherDependencies: Boolean = project.configurations
				.filter { configuration: Configuration ->
					configuration.name != "api"
				}
				.flatMap(Configuration::getDependencies)
				.isNotEmpty()

			check(!hasOtherDependencies) {
				"Project must not have dependencies that aren't in the configuration \"api\""
			}
		}

		if (projectType.isNamespace()) {
			checkProjectHasNoDependencies(project)
		}
	}

	private fun checkProjectChildren(project: Project) {
		val projectType: ProjectType = project.getProjectType()

		if (projectType.isStandalone()) {
			check(project.childProjects.isEmpty()) {
				"Project must not have any child projects (${project.childProjects.values.joinToString()})"
			}
		}

		if (projectType.isBranch()) {
			this.warnIfProjectHasLessThanTwoChildren(project)

			val invalidChildren: List<Project> = project.childProjects.values
				.filterNot { childProject: Project ->
					childProject.plugins.hasPlugin<ProjectStructureEnforcementPlugin>()
				}

			check(invalidChildren.isEmpty()) {
				"Project's children must have a project type marker plugin applied to them" +
					" (${invalidChildren.joinToString()})"
			}
		}

		if (projectType.isService()) {
			val hasSingleServiceInterfaceChildProject: Boolean = project.childProjects.values
				.singleOrNull { childProject: Project ->
					childProject.getProjectTypeOrNull()?.isServiceInterface() ?: false
				} != null

			check(hasSingleServiceInterfaceChildProject) {
				"Project must have exactly one service interface child project"
			}

			val serviceImplementationChildProjects: List<Project> = project.childProjects.values
				.filter { childProject: Project ->
					childProject.getProjectTypeOrNull()?.isServiceImplementation() ?: false
				}

			check(serviceImplementationChildProjects.isNotEmpty()) {
				"Project must have at least one service implementation child project"
			}

			if (serviceImplementationChildProjects.size > 1) {
				val x: Boolean = serviceImplementationChildProjects
					.all { childProject: Project ->
						childProject.name.matches(Regex("^impl-.+$"))
					}

				check(x) { // TODO

				}
			}

			val hasSingleServiceWiringChildProject: Boolean = project.childProjects.values
				.singleOrNull { childProject: Project ->
					childProject.getProjectTypeOrNull()?.isServiceWiring() ?: false
				} != null

			check(hasSingleServiceWiringChildProject) {
				"Project must have exactly one service wiring child project"
			}

			val x: Boolean = project.childProjects.values
				.all { childProject: Project ->
					childProject.getProjectTypeOrNull()?.isServiceChild() ?: false
				}

			check(x) { // TODO

			}
		}
	}

	private fun warnIfProjectHasLessThanTwoChildren(project: Project) {
		if (project.childProjects.size >= 2) {
			return
		}

		project.logger.warn("Project has less than 2 children")
	}
}

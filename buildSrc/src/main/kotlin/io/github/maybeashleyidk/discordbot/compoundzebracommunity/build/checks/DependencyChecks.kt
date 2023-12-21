package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.checks

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins.getProjectTypeOrNull
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceImplementation
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceInterface
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.isServiceWiring
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ProjectDependency

internal fun checkProjectHasNoDependencies(project: Project) {
	val hasNoDependencies: Boolean = project.configurations
		.flatMap(Configuration::getDependencies)
		.isEmpty()

	check(hasNoDependencies) {
		"Project must not have any dependencies"
	}
}

internal fun checkProjectHasNoNonApiDependencies(project: Project) {

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

internal fun checkProjectHasNoParentCompositeProjectDependencies(project: Project) {
	val parentCompositeProject: Project = (project.parent ?: return)
		.takeIf { parentProject: Project ->
			parentProject.getProjectTypeOrNull() is ProjectType.Composite
		}
		?: return

	val hasNoParentCompositeProjectDependencies: Boolean = project.getAllProjectDependencies()
		.none { dependency: Project ->
			dependency == parentCompositeProject
		}

	check(hasNoParentCompositeProjectDependencies) {
		"Project must not have dependencies on the parent composite project"
	}
}

internal fun checkProjectHasNoNamespaceProjectDependencies(project: Project) {
	val namespaceProjectDependencies: List<Project> = project.getAllProjectDependencies()
		.filter { dependency: Project ->
			dependency.getProjectTypeOrNull() is ProjectType.Namespace
		}
		.toList()

	check(namespaceProjectDependencies.isEmpty()) {
		"Project must not have dependencies on namespace projects (${namespaceProjectDependencies.joinToString()})"
	}
}

internal fun checkProjectHasNoServiceInterfaceProjectDependencies(project: Project) {
	val serviceInterfaceProjectDependencies: List<Project> = project.getAllProjectDependencies()
		.filter { dependency: Project ->
			dependency.getProjectTypeOrNull()?.isServiceInterface() ?: false
		}
		.toList()

	check(serviceInterfaceProjectDependencies.isEmpty()) {
		"Project must not have dependencies on service interface projects" +
			" (${serviceInterfaceProjectDependencies.joinToString()})"
	}
}

internal fun checkProjectHasNoServiceImplementationProjectDependencies(project: Project) {
	val serviceImplementationProjectDependencies: List<Project> = project.getAllProjectDependencies()
		.filter { dependency: Project ->
			dependency.getProjectTypeOrNull()?.isServiceImplementation() ?: false
		}
		.toList()

	check(serviceImplementationProjectDependencies.isEmpty()) {
		"Project must not have dependencies on service implementation projects" +
			" (${serviceImplementationProjectDependencies.joinToString()})"
	}
}

internal fun checkProjectHasNoServiceWiringProjectDependencies(project: Project) {
	val serviceWiringProjectDependencies: List<Project> = project.getAllProjectDependencies()
		.filter { dependency: Project ->
			dependency.getProjectTypeOrNull()?.isServiceWiring() ?: false
		}
		.toList()

	check(serviceWiringProjectDependencies.isEmpty()) {
		"Project must not have dependencies on service wiring projects" +
			" (${serviceWiringProjectDependencies.joinToString()})"
	}
}

private fun Project.getAllProjectDependencies(): Sequence<Project> {
	return this.configurations
		.asSequence()
		.flatMap(Configuration::getDependencies)
		.filterIsInstance<ProjectDependency>()
		.map(ProjectDependency::getDependencyProject)
}

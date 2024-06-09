package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.requireType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.selectPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.flattenDepth
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency

internal object ProjectDependenciesEnforcement {

	fun enforceProjectsDependencies(projectTree: Tree<Project>) {
		for (project: Project in projectTree.flattenDepth()) {
			this.enforceProjectDependenciesFor(project)
		}
	}

	private fun enforceProjectDependenciesFor(project: Project) {
		val dependenciesPolicy: ProjectDependenciesPolicy = project.requireType().selectPolicy().dependenciesPolicy

		val implementationDependencies: Sequence<DependencyWithType> = project.configurations.findByName("implementation")
			?.dependencies
			?.asSequence()
			.orEmpty()
			.map { dependency: Dependency ->
				DependencyWithType(DependencyType.IMPLEMENTATION, dependency)
			}

		val apiDependencies: Sequence<DependencyWithType> = project.configurations.findByName("api")
			?.dependencies
			?.asSequence()
			.orEmpty()
			.map { dependency: Dependency ->
				DependencyWithType(DependencyType.API, dependency)
			}

		dependenciesPolicy.enforce(project, dependencies = (implementationDependencies + apiDependencies))
	}
}

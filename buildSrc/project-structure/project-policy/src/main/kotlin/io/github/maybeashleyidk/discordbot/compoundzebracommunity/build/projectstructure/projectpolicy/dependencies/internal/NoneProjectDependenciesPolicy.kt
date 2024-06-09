package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import org.gradle.api.Project

internal object NoneProjectDependenciesPolicy : ProjectDependenciesPolicy {

	override fun enforce(project: Project, dependencies: Sequence<DependencyWithType>) {
		val dependencyList: List<DependencyWithType> = dependencies.toList()

		check(dependencyList.isEmpty()) {
			"The $project must not have any dependencies (${dependencyList.joinToString()})"
		}
	}
}

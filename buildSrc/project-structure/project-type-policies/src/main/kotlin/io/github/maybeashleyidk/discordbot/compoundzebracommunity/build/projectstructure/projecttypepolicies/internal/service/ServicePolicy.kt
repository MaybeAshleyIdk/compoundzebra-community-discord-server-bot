package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.getTypeOrNull
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.COMPOSITE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.isParentOf
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

internal val SERVICE_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = ProjectNamePolicy.AllPermissive,
		pluginsPolicy = COMPOSITE_POLICY.pluginsPolicy,
		sourceCodePolicy = COMPOSITE_POLICY.sourceCodePolicy,
		dependenciesPolicy = ServiceDependenciesPolicy,
		childrenPolicy = ServiceChildrenPolicy,
	)

private object ServiceDependenciesPolicy : ProjectDependenciesPolicy {

	override fun enforce(project: Project, dependencies: Sequence<DependencyWithType>) {
		var hasChildInterfaceDependency = false

		for (dependencyWithType: DependencyWithType in dependencies) {
			this.enforce(project, dependencyWithType)

			check(!hasChildInterfaceDependency) {
				"The $project must not have multiple dependencies on service-interface child projects"
			}

			hasChildInterfaceDependency = true
		}

		check(hasChildInterfaceDependency) {
			"The $project must have a dependency on a service-interface child project"
		}
	}

	private fun enforce(project: Project, dependencyWithType: DependencyWithType) {
		when (dependencyWithType.type) {
			DependencyType.IMPLEMENTATION -> {
				error("The $project must not have any implementation dependencies (${dependencyWithType.dependency})")
			}

			DependencyType.API -> Unit
		}

		val apiDependency: Dependency = dependencyWithType.dependency

		check(apiDependency is ProjectDependency) {
			"The $project must not have any non-project API dependencies ($apiDependency)"
		}

		check(project.isParentOf(apiDependency.dependencyProject)) {
			"The $project must only have dependencies on direct child projects ($apiDependency)"
		}
	}
}

private object ServiceChildrenPolicy : ProjectChildrenPolicy {

	override fun enforce(project: Project, children: List<Project>) {
		val childrenTypes: List<ProjectType> = children.mapNotNull(Project::getTypeOrNull)

		check(childrenTypes.count(ProjectType::isServiceInterface) == 1) {
			"The $project must have exactly 1 child of type service-interface"
		}
		check(childrenTypes.count(ProjectType::isServiceImplementation) == 1) {
			"The $project must have exactly 1 child of type service-implementation (standalone or composite)"
		}

		check(childrenTypes.size == 2) {
			"The $project must have exactly 2 children"
		}
	}
}

private fun ProjectType.isServiceInterface(): Boolean {
	return when (this) {
		ProjectType.STANDALONE -> false
		ProjectType.NAMESPACE -> false
		ProjectType.COMPOSITE -> false
		ProjectType.SERVICE -> false
		ProjectType.SERVICE_INTERFACE -> true
		ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> false
		ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> false
	}
}

private fun ProjectType.isServiceImplementation(): Boolean {
	return when (this) {
		ProjectType.STANDALONE -> false
		ProjectType.NAMESPACE -> false
		ProjectType.COMPOSITE -> false
		ProjectType.SERVICE -> false
		ProjectType.SERVICE_INTERFACE -> false
		ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> true
		ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> true
	}
}

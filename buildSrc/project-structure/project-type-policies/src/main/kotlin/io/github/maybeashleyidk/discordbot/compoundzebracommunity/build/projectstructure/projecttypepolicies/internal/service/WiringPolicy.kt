package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.toProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.getTypeOrNull
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.STANDALONE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.areSiblings
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

internal val SERVICE_WIRING_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = ProjectNamePolicy.FixedString("wiring".toProjectName(), isReserved = false),
		pluginsPolicy = STANDALONE_POLICY.pluginsPolicy,
		sourceCodePolicy = STANDALONE_POLICY.sourceCodePolicy,
		dependenciesPolicy = ServiceWiringDependenciesPolicy,
		childrenPolicy = STANDALONE_POLICY.childrenPolicy,
	)

private object ServiceWiringDependenciesPolicy : ProjectDependenciesPolicy {

	override fun enforce(project: Project, dependencies: Sequence<DependencyWithType>) {
		var hasSiblingImplDependency = false

		for (dependencyWithType: DependencyWithType in dependencies) {
			if (this.enforce(project, dependencyWithType)) {
				check(!hasSiblingImplDependency) {
					"The $project must not have multiple dependencies on service-implementation sibling projects"
				}

				hasSiblingImplDependency = true
			}
		}

		check(hasSiblingImplDependency) {
			"The $project must have a dependency on a service-implementation sibling project"
		}
	}

	private fun enforce(project: Project, dependencyWithType: DependencyWithType): Boolean {
		val dependency: Dependency = dependencyWithType.dependency

		if (dependency !is ProjectDependency) {
			return false
		}

		check(areSiblings(project, dependency.dependencyProject)) {
			"The $project must only have project dependencies of siblings ($dependency)"
		}

		val siblingType: ProjectType? = dependency.dependencyProject.getTypeOrNull()

		check((siblingType != null) && siblingType.isServiceImplementation()) {
			"The $project must only have project dependencies on service-implementation siblings"
		}

		when (dependencyWithType.type) {
			DependencyType.IMPLEMENTATION -> {
				val msg: String = "The $project's dependency on its service-implementation sibling must be in " +
					"the configuration \"api\""

				error(msg)
			}

			DependencyType.API -> Unit
		}

		return true
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
		ProjectType.SERVICE_WIRING -> false
	}
}

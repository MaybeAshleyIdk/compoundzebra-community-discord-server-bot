package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.getTypeOrNull
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.COMPOSITE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.STANDALONE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.areSiblings
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

private val SERVICE_IMPLEMENTATION_NAME_POLICY: ProjectNamePolicy = ProjectNamePolicy.Pattern(Regex("^impl(-.+)?$"))

internal val STANDALONE_SERVICE_IMPLEMENTATION_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = SERVICE_IMPLEMENTATION_NAME_POLICY,
		pluginsPolicy = STANDALONE_POLICY.pluginsPolicy,
		sourceCodePolicy = STANDALONE_POLICY.sourceCodePolicy,
		dependenciesPolicy = StandaloneServiceImplementationDependenciesPolicy,
		childrenPolicy = STANDALONE_POLICY.childrenPolicy,
	)

internal val COMPOSITE_SERVICE_IMPLEMENTATION_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = SERVICE_IMPLEMENTATION_NAME_POLICY,
		pluginsPolicy = COMPOSITE_POLICY.pluginsPolicy,
		sourceCodePolicy = COMPOSITE_POLICY.sourceCodePolicy,
		dependenciesPolicy = COMPOSITE_POLICY.dependenciesPolicy,
		childrenPolicy = COMPOSITE_POLICY.childrenPolicy,
	)

private object StandaloneServiceImplementationDependenciesPolicy : ProjectDependenciesPolicy {

	override fun enforce(project: Project, dependencies: Sequence<DependencyWithType>) {
		var hasSiblingInterfaceDependency = false

		for (dependencyWithType: DependencyWithType in dependencies) {
			if (this.enforce(project, dependencyWithType)) {
				check(!hasSiblingInterfaceDependency) {
					"The $project must not have multiple dependencies on service-interface sibling projects"
				}

				hasSiblingInterfaceDependency = true
			}
		}

		check(hasSiblingInterfaceDependency) {
			"The $project must have a dependency on a service-interface sibling project"
		}
	}

	private fun enforce(project: Project, dependencyWithType: DependencyWithType): Boolean {
		val dependency: Dependency = dependencyWithType.dependency

		if (dependency !is ProjectDependency) {
			return false
		}

		if (!(areSiblings(project, dependency.dependencyProject))) {
			return false
		}

		val siblingType: ProjectType? = dependency.dependencyProject.getTypeOrNull()

		check((siblingType != null) && siblingType.isServiceInterface()) {
			"The $project must only have sibling project dependencies on service-interface projects"
		}

		when (dependencyWithType.type) {
			DependencyType.IMPLEMENTATION -> {
				val msg: String = "The $project's dependency on its service-interface sibling must be in " +
					"the configuration \"api\""

				error(msg)
			}

			DependencyType.API -> Unit
		}

		return true
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

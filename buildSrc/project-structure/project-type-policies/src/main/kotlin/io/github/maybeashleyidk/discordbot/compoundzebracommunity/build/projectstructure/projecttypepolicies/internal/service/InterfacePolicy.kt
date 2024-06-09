package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.toProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.STANDALONE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.areSiblings
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

internal val SERVICE_INTERFACE_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = ProjectNamePolicy.FixedString("api".toProjectName(), isReserved = true),
		pluginsPolicy = STANDALONE_POLICY.pluginsPolicy,
		sourceCodePolicy = STANDALONE_POLICY.sourceCodePolicy,
		dependenciesPolicy = ServiceInterfaceDependenciesPolicy,
		childrenPolicy = STANDALONE_POLICY.childrenPolicy,
	)

private object ServiceInterfaceDependenciesPolicy : ProjectDependenciesPolicy {

	override fun enforce(project: Project, dependencies: Sequence<DependencyWithType>) {
		for (dependencyWithType: DependencyWithType in dependencies) {
			this.enforce(project, dependencyWithType)
		}
	}

	private fun enforce(project: Project, dependencyWithType: DependencyWithType) {
		val dependency: Dependency = dependencyWithType.dependency

		if (dependency !is ProjectDependency) {
			return
		}

		check(!(areSiblings(project, dependency.dependencyProject))) {
			"The $project must not have project dependencies of siblings ($dependency)"
		}
	}
}

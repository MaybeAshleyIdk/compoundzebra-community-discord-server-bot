package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectPluginsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.SourceCodePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy.DependencyWithType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.isParentOf
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.selectEnumValues
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

private val ALLOWED_CHILD_TYPES: Set<ProjectType> =
	selectEnumValues<ProjectType> { projectType: ProjectType ->
		when (projectType) {
			ProjectType.STANDALONE -> true
			ProjectType.NAMESPACE -> true
			ProjectType.COMPOSITE -> true
			ProjectType.SERVICE -> true
			ProjectType.SERVICE_INTERFACE -> false
			ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> false
			ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> false
		}
	}

internal val COMPOSITE_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = ProjectNamePolicy.AllPermissive,
		pluginsPolicy = ProjectPluginsPolicy.JAVA_LIBRARY_WITHOUT_KTJVM,
		sourceCodePolicy = SourceCodePolicy.NONE,
		dependenciesPolicy = CompositeDependenciesPolicy,
		childrenPolicy = ProjectChildrenPolicies.required1Recommended2(allowedTypes = ALLOWED_CHILD_TYPES),
	)

private object CompositeDependenciesPolicy : ProjectDependenciesPolicy {

	override fun enforce(project: Project, dependencies: Sequence<DependencyWithType>) {
		for (dependencyWithType: DependencyWithType in dependencies) {
			this.enforce(project, dependencyWithType)
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

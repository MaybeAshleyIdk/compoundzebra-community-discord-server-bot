package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency

public interface ProjectDependenciesPolicy {

	public enum class DependencyType {
		IMPLEMENTATION,
		API,
	}

	public data class DependencyWithType(
		public val type: DependencyType,
		public val dependency: Dependency,
	)

	public fun enforce(project: Project, dependencies: Sequence<DependencyWithType>)
}

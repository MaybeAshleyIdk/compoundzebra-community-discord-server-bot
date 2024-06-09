package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicy

public data class ProjectDetailsPolicy(
	public val namePolicy: ProjectNamePolicy,
	public val pluginsPolicy: ProjectPluginsPolicy,
	public val sourceCodePolicy: SourceCodePolicy,
	public val dependenciesPolicy: ProjectDependenciesPolicy,
	public val childrenPolicy: ProjectChildrenPolicy,
	// TODO: add project relationships policy
)

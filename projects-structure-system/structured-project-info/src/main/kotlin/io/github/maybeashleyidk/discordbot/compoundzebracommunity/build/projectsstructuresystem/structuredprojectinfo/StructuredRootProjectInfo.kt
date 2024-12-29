package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import java.io.Serializable

public data class StructuredRootProjectInfo(
	public val topLevelProjects: Map<ProjectName, StructuredProjectInfoDetails>,
) : Serializable

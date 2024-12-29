package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails as ProjectInclusion
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo.StructuredProjectInfoDetails as StructuredProject

@Suppress("ktlint:standard:max-line-length")
internal fun Map<ProjectName, ProjectInclusion>.toStructuredProjectInfoDetailsMap(): Map<ProjectName, StructuredProject> {
	return this
		.mapValues { (_: ProjectName, projectInclusionInfoDetails: ProjectInclusion) ->
			projectInclusionInfoDetails.toStructuredProjectInfoDetails()
		}
}

private fun ProjectInclusion.Namespace.toStructuredProjectInfoDetails(): StructuredProject.Namespace {
	val childProjects: Map<ProjectName, StructuredProject> =
		this.childProjects.toStructuredProjectInfoDetailsMap()

	return StructuredProject.Namespace(childProjects)
}

private fun ProjectInclusion.Composite.toStructuredProjectInfoDetails(): StructuredProject.Composite {
	val childProjects: Map<ProjectName, StructuredProject> =
		this.childProjects.toStructuredProjectInfoDetailsMap()

	return StructuredProject.Composite(childProjects, this.publicChildProjectName)
}

private fun ProjectInclusion.Service.toStructuredProjectInfoDetails(): StructuredProject.Service {
	val implementationProjects: Map<ServiceImplementationName, StructuredProject.Service.Implementation> =
		this.implementationProjects
			.mapValues { (_: ServiceImplementationName, projectInclusionInfoDetails: ProjectInclusion.Service.Implementation) ->
				projectInclusionInfoDetails.toStructuredProjectInfoDetails()
			}

	return StructuredProject.Service(implementationProjects)
}

private fun ProjectInclusion.toStructuredProjectInfoDetails(): StructuredProject {
	return when (this) {
		is ProjectInclusion.Service.Implementation -> this.toStructuredProjectInfoDetails()
		is ProjectInclusion.Namespace -> this.toStructuredProjectInfoDetails()
		is ProjectInclusion.Service -> this.toStructuredProjectInfoDetails()
	}
}

@Suppress("ktlint:standard:max-line-length")
private fun ProjectInclusion.Service.Implementation.toStructuredProjectInfoDetails(): StructuredProject.Service.Implementation {
	return when (this) {
		is ProjectInclusion.Standalone -> StructuredProject.Standalone
		is ProjectInclusion.Composite -> this.toStructuredProjectInfoDetails()
	}
}

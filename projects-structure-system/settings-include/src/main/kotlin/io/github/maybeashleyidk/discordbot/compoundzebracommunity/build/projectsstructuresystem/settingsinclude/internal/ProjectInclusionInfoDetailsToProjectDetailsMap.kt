package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo.ProjectDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails as ProjectInclusion

internal fun Map<ProjectName, ProjectInclusion>.toProjectDetailsMap(): Map<ProjectName, ProjectDetails.NonRoot> {
	return this
		.mapValues { (_: ProjectName, projectInclusionInfoDetails: ProjectInclusion) ->
			projectInclusionInfoDetails.toProjectDetails()
		}
}

private fun ProjectInclusion.Namespace.toProjectDetails(): ProjectDetails.Namespace {
	return ProjectDetails.Namespace(this.childProjects.toProjectDetailsMap())
}

private fun ProjectInclusion.Composite.toProjectDetails(): ProjectDetails.Composite.NonServiceChild {
	return ProjectDetails.Composite.NonServiceChild(
		this.childProjects.toProjectDetailsMap(),
		this.publicChildProjectName,
	)
}

private fun ProjectInclusion.Composite.toProjectDetailsSi(): ProjectDetails.Composite.ServiceImplementation {
	return ProjectDetails.Composite.ServiceImplementation(
		this.childProjects.toProjectDetailsMap(),
		this.publicChildProjectName,
	)
}

private fun ProjectInclusion.Service.toProjectDetails(): ProjectDetails.Service {
	val implementationProjects: Map<ServiceImplementationName, ProjectDetails.Service.Implementation> =
		this.implementationProjects
			.mapValues { (_: ServiceImplementationName, implProject: ProjectInclusion.Service.Implementation) ->
				implProject.toProjectDetailsSi()
			}

	return ProjectDetails.Service(implementationProjects)
}

private fun ProjectInclusion.Service.Implementation.toProjectDetails(): ProjectDetails.NonServiceChild {
	return when (this) {
		is ProjectInclusion.Standalone -> ProjectDetails.Standalone.NonServiceChild
		is ProjectInclusion.Composite -> this.toProjectDetails()
	}
}

private fun ProjectInclusion.Service.Implementation.toProjectDetailsSi(): ProjectDetails.Service.Implementation {
	return when (this) {
		is ProjectInclusion.Standalone -> ProjectDetails.Standalone.ServiceImplementation
		is ProjectInclusion.Composite -> this.toProjectDetailsSi()
	}
}

private fun ProjectInclusion.toProjectDetails(): ProjectDetails.NonServiceChild {
	return when (this) {
		is ProjectInclusion.Service.Implementation -> this.toProjectDetails()
		is ProjectInclusion.Namespace -> this.toProjectDetails()
		is ProjectInclusion.Service -> this.toProjectDetails()
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectpath.ProjectPath
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo.ProjectDetails
import org.gradle.api.IsolatedAction
import org.gradle.api.Project

internal class ProjectEnforcementIsolatedAction(
	private val rootProjectDetails: ProjectDetails.Root,
) : IsolatedAction<Project> {

	override fun execute(project: Project) {
		val projectPath: ProjectPath = ProjectPath.ofString(project.path)!!

		val projectDetails: ProjectDetails? = this.rootProjectDetails.findByPath(projectPath)

		println("TEST: $project: $projectDetails")
	}
}


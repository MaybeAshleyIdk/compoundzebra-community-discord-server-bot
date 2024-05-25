package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal.utils.unique
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.toProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.isAllowedBy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.isNotReservedBy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.requireType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.selectPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.flattenDepth
import org.gradle.api.Project

internal object ProjectNamesEnforcement {

	fun enforceProjectsNames(projectTree: Tree<Project>) {
		for (project: Project in projectTree.flattenDepth()) {
			this.enforceProjectName(targetProject = project, projectTree)
		}
	}

	private fun enforceProjectName(targetProject: Project, projectTree: Tree<Project>) {
		val targetProjectType: ProjectType = targetProject.requireType()
		val namePolicy: ProjectNamePolicy = targetProjectType.selectPolicy().namePolicy

		check(targetProject.getProjectName().isAllowedBy(namePolicy)) {
			"The name of the $targetProject is illegal. It must match ${namePolicy.description}"
		}

		val otherNamePolicies: Sequence<ProjectNamePolicy> = projectTree
			.flattenDepth()
			.map { project: Project ->
				project.requireType().selectPolicy().namePolicy
			}
			.filter { otherNamePolicy: ProjectNamePolicy ->
				(otherNamePolicy != namePolicy)
			}
			.unique()

		for (otherNamePolicy: ProjectNamePolicy in otherNamePolicies) {
			check(targetProject.getProjectName().isNotReservedBy(otherNamePolicy)) {
				"The name of the $targetProject is illegal. It must not match ${otherNamePolicy.description}"
			}
		}
	}
}

private fun Project.getProjectName(): ProjectName {
	return this.name.toProjectName()
}

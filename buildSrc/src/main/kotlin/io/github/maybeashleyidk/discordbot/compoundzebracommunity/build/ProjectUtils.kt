package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build

import org.gradle.api.Project

internal fun Project.getSiblingProjects(): List<Project> {
	val parentProject: Project = this.parent
		?: return emptyList()

	return parentProject.childProjects.values
		.filter { parentChildProject: Project ->
			parentChildProject != this
		}
}

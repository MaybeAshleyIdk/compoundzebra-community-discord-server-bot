package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children

import org.gradle.api.Project

public fun interface ProjectChildrenPolicy {

	public fun enforce(project: Project, children: List<Project>)
}

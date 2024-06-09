package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicy
import org.gradle.api.Project

internal object NoneProjectChildrenPolicy : ProjectChildrenPolicy {

	override fun enforce(project: Project, children: List<Project>) {
		if (children.isEmpty()) {
			return
		}

		error("The $project must not have any children")
	}
}

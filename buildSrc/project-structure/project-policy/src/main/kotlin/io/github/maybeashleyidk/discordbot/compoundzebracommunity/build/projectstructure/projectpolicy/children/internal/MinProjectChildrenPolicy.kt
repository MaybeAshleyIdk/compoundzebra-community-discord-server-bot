package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.requireType
import org.gradle.api.Project

internal class MinProjectChildrenPolicy(
	private val minRequired: Int,
	private val minRecommended: Int,
	private val allowedTypes: Set<ProjectType>,
) : ProjectChildrenPolicy {

	init {
		require(this.minRequired <= this.minRecommended)
		require(this.allowedTypes.isNotEmpty())
	}

	override fun enforce(project: Project, children: List<Project>) {
		check(children.size >= this.minRequired) {
			if (this.minRequired.isEnglishPluralOne()) {
				"The $project must have at least ${this.minRequired} child"
			} else {
				"The $project must have at least ${this.minRequired} children"
			}
		}

		if (children.size < this.minRecommended) {
			project.logger.warn("The $project should have at least ${this.minRecommended} children")
		}

		for (child: Project in children) {
			check(child.requireType() in this.allowedTypes) {
				"The $child must be one of the following types: ${this.allowedTypes.joinToString()}"
			}
		}
	}
}

private fun Int.isEnglishPluralOne(): Boolean {
	return (this == 1)
}

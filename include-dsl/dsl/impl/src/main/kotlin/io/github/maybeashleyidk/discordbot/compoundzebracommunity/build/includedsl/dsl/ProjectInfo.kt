package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.projectname.ProjectName

internal data class ProjectInfo(
	val parent: ProjectInfo?,
	val name: ProjectName,
) {

	fun buildPath(): String {
		val parentPath: String =
			if (this.parent != null) {
				this.parent.buildPath()
			} else {
				""
			}

		return "$parentPath:${this.name}"
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectpath

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName

@JvmInline
public value class ProjectPath private constructor(private val pathNames: List<ProjectName>) {

	public operator fun plus(projectName: ProjectName): ProjectPath {
		return ProjectPath(this.pathNames + projectName)
	}

	override fun toString(): String {
		return this.pathNames.joinToString(prefix = ":", separator = ":")
	}

	public companion object {

		public val Root: ProjectPath = ProjectPath(pathNames = emptyList())
	}
}

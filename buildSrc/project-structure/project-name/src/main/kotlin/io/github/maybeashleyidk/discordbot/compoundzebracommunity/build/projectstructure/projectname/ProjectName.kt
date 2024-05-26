package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname

@JvmInline
public value class ProjectName private constructor(private val string: String) {

	public companion object {

		public fun ofStringOrNull(nameString: String): ProjectName? {
			if (!(nameString.isValidProjectName())) {
				return null
			}

			return ProjectName(nameString)
		}
	}

	init {
		require(this.string.isValidProjectName()) {
			"Invalid project name \"${this.string}\""
		}
	}

	override fun toString(): String {
		return this.string
	}
}

public fun String.toProjectName(): ProjectName {
	val projectName: ProjectName? = ProjectName.ofStringOrNull(nameString = this)

	checkNotNull(projectName) {
		"Invalid project name \"$projectName\""
	}

	return projectName
}

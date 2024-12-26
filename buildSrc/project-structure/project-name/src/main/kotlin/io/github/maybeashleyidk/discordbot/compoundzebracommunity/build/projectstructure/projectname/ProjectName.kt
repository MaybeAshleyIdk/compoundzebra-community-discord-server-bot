package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname

@JvmInline
public value class ProjectName private constructor(private val nameString: String) {

	init {
		require(this.nameString.isValidProjectName()) {
			"Invalid project name \"${this.nameString}\""
		}
	}

	override fun toString(): String {
		return this.nameString
	}

	public companion object {

		public fun ofString(nameString: String): ProjectName? {
			if (!(nameString.isValidProjectName())) {
				return null
			}

			return ProjectName(nameString)
		}
	}
}

public fun String.toProjectName(): ProjectName {
	val projectName: ProjectName? = ProjectName.ofString(nameString = this)

	checkNotNull(projectName) {
		"Invalid project name \"$projectName\""
	}

	return projectName
}

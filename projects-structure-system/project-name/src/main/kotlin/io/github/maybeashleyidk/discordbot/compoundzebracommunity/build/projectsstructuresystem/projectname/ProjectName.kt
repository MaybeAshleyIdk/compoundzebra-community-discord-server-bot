package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname

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

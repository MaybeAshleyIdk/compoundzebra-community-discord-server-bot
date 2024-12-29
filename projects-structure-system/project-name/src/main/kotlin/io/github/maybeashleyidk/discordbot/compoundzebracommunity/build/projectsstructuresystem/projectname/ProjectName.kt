package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname

import java.io.Serializable

@JvmInline
public value class ProjectName private constructor(private val nameString: String) : Serializable {

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

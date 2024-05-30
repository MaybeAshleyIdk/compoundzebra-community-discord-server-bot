package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.projectname

@JvmInline
public value class ProjectName private constructor(private val string: String) {

	public companion object {

		public fun ofStringOrNull(nameString: String): ProjectName? {
			if (!(nameString.isValidProjectName())) {
				return null
			}

			return ProjectName(nameString)
		}

		public fun ofString(nameString: String): ProjectName {
			val name: ProjectName? = this.ofStringOrNull(nameString)

			requireNotNull(name) {
				"Invalid project name \"$nameString\""
			}

			return name
		}
	}

	init {
		require(this.string.isValidProjectName()) {
			"Invalid project name \"${this.string}\""
		}
	}

	public fun isLegal(): Boolean {
		// Projects cannot be called "name" because the type-safe project accessors already have
		// a property called `name` and if a project name would be "name" then it would lead to conflicts.
		return (this.string != "name")
	}

	override fun toString(): String {
		return this.string
	}
}

public fun ProjectName.isNotLegal(): Boolean {
	return !(this.isLegal())
}

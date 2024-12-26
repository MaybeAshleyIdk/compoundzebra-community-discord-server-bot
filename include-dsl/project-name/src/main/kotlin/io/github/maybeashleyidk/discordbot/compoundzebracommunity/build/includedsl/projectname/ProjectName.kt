package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.projectname

@JvmInline
public value class ProjectName private constructor(private val nameString: String) {

	init {
		require(this.nameString.isValidProjectName()) {
			"Invalid project name \"${this.nameString}\""
		}
	}

	public fun isLegal(): Boolean {
		// Projects cannot be called "name" because the type-safe project accessors already have
		// a property called `name` and if a project name would be "name" then it would lead to conflicts.
		return (this.nameString != "name")
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

public fun ProjectName.isNotLegal(): Boolean {
	return !(this.isLegal())
}

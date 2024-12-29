package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import java.io.Serializable

private val IMPL_PROJECT_NAME: ProjectName = ProjectName.ofString("impl")!!
private const val IMPL_PREFIX: String = "impl-"

@JvmInline
public value class ServiceImplementationName private constructor(
	private val projectNameWithoutPrefix: ProjectName?,
) : Serializable {

	public fun toFullProjectName(): ProjectName {
		if (this.projectNameWithoutPrefix == null) {
			return IMPL_PROJECT_NAME
		}

		return ProjectName.ofString(IMPL_PREFIX + this.projectNameWithoutPrefix.toString())!!
	}

	override fun toString(): String {
		return this.projectNameWithoutPrefix?.toString().orEmpty()
	}

	public companion object {

		public val None: ServiceImplementationName = ServiceImplementationName(projectNameWithoutPrefix = null)

		public fun ofString(nameString: String): ServiceImplementationName? {
			val projectNameWithoutPrefix: ProjectName = ProjectName.ofString(nameString)
				?: return null

			return ServiceImplementationName(projectNameWithoutPrefix)
		}

		public fun fromFullProjectName(projectName: ProjectName): ServiceImplementationName? {
			if (projectName == IMPL_PROJECT_NAME) {
				return this.None
			}

			val projectNameWithoutPrefix: ProjectName? = projectName.toString()
				.removePrefixOrNull(IMPL_PREFIX)
				?.let(ProjectName::ofString)

			if (projectNameWithoutPrefix != null) {
				return ServiceImplementationName(projectNameWithoutPrefix)
			}

			return null
		}
	}
}

private fun String.removePrefixOrNull(prefix: String): String? {
	return this.removePrefix(prefix)
		.takeIf { thisWithoutPrefix: String ->
			thisWithoutPrefix != this
		}
}

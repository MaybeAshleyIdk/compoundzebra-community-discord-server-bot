package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName

public sealed class ProjectInclusionInfoDetails {

	public abstract val directoryName: String?

	public data class Standalone(override val directoryName: String?) : Service.Implementation()

	public data class Namespace(
		override val directoryName: String?,
		public val childProjects: Map<ProjectName, ProjectInclusionInfoDetails>,
	) : ProjectInclusionInfoDetails()

	public data class Composite(
		override val directoryName: String?,
		val childProjects: Map<ProjectName, ProjectInclusionInfoDetails>,
		val publicChildProjectName: ProjectName,
	) : Service.Implementation() {

		init {
			require(this.publicChildProjectName in this.childProjects.keys)
		}
	}

	public data class Service(
		override val directoryName: String?,
		public val implementationProjects: Map<ServiceImplementationName, Implementation>,
	) : ProjectInclusionInfoDetails() {

		public sealed class Implementation : ProjectInclusionInfoDetails()

		init {
			require(this.implementationProjects.isNotEmpty())
		}
	}
}

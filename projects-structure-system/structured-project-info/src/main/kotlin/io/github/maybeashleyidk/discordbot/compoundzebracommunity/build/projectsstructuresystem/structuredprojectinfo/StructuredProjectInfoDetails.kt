package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName
import java.io.Serializable

public sealed class StructuredProjectInfoDetails : Serializable {

	public data object Standalone : Service.Implementation() {

		@Suppress("unused")
		private fun readResolve(): Any = Standalone
	}

	public data class Namespace(
		public val childProjects: Map<ProjectName, StructuredProjectInfoDetails>,
	) : StructuredProjectInfoDetails()

	public data class Composite(
		val childProjects: Map<ProjectName, StructuredProjectInfoDetails>,
		val publicChildProjectName: ProjectName,
	) : Service.Implementation() {

		init {
			require(this.publicChildProjectName in this.childProjects.keys)
		}
	}

	public data class Service(
		public val implementationProjects: Map<ServiceImplementationName, Implementation>,
	) : StructuredProjectInfoDetails() {

		public data object Interface : StructuredProjectInfoDetails() {

			@Suppress("unused")
			private fun readResolve(): Any = Interface
		}

		public sealed class Implementation : StructuredProjectInfoDetails()

		init {
			require(this.implementationProjects.isNotEmpty())
		}
	}
}

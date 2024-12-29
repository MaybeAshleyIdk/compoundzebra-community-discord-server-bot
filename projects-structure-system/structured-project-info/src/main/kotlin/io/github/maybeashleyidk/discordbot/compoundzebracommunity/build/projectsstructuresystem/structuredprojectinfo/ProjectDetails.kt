package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName
import java.io.Serializable

// TODO: revert this again
//       we can imply if a project is a service child by just looking at its parent

public sealed interface ProjectDetails : Serializable {

	public sealed interface NonRoot : ProjectDetails

	public sealed interface NonServiceChild : NonRoot

	public data class Root(public val topLevelProjects: Map<ProjectName, NonRoot>) : ProjectDetails

	public sealed class Standalone : NonRoot {

		public data object NonServiceChild : Standalone(), ProjectDetails.NonServiceChild {

			@Suppress("unused")
			private fun readResolve(): Any = NonServiceChild
		}

		public data object ServiceInterface : Standalone() {

			@Suppress("unused")
			private fun readResolve(): Any = ServiceInterface
		}

		public data object ServiceImplementation : Standalone(), Service.Implementation {

			@Suppress("unused")
			private fun readResolve(): Any = ServiceImplementation
		}
	}

	public data class Namespace(public val childProjects: Map<ProjectName, NonRoot>) : NonServiceChild

	public sealed class Composite : NonRoot {

		@ConsistentCopyVisibility
		public data class Common internal constructor(
			public val childProjects: Map<ProjectName, NonRoot>,
			public val publicChildProjectName: ProjectName,
		) {

			init {
				require(this.publicChildProjectName in this.childProjects.keys)
			}
		}

		public abstract val details: Common

		public data class NonServiceChild(override val details: Common) : Composite(), ProjectDetails.NonServiceChild {

			public constructor(
				childProjects: Map<ProjectName, NonRoot>,
				publicChildProjectName: ProjectName,
			) : this(Common(childProjects, publicChildProjectName))
		}

		public data class ServiceImplementation(override val details: Common) : Composite(), Service.Implementation {

			public constructor(
				childProjects: Map<ProjectName, NonRoot>,
				publicChildProjectName: ProjectName,
			) : this(Common(childProjects, publicChildProjectName))
		}
	}

	public data class Service(
		public val implementationProjects: Map<ServiceImplementationName, Implementation>,
	) : NonServiceChild {

		public sealed interface Implementation : NonRoot
	}
}

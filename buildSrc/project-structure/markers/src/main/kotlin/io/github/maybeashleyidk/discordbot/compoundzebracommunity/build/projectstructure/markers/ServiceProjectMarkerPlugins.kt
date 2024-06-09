package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.markers

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.markers.internal.markAsProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ServiceProjectMarkerPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.markAsProjectType(ProjectType.SERVICE)
	}
}

public class ServiceInterfaceProjectMarkerPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.markAsProjectType(ProjectType.SERVICE_INTERFACE)
	}
}

public class StandaloneServiceImplementationProjectMarkerPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.markAsProjectType(ProjectType.SERVICE_IMPLEMENTATION_STANDALONE)
	}
}

public class CompositeServiceImplementationProjectMarkerPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.markAsProjectType(ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE)
	}
}

public class ServiceWiringProjectMarkerPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.markAsProjectType(ProjectType.SERVICE_WIRING)
	}
}

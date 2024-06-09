package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.markers

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.markers.internal.markAsProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import org.gradle.api.Plugin
import org.gradle.api.Project

public class CompositeProjectMarkerPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.markAsProjectType(ProjectType.COMPOSITE)
	}
}

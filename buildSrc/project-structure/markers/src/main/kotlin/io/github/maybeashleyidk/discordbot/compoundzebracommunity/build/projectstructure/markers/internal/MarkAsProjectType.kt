package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.markers.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.ProjectStructureEnforcementPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.setType
import org.gradle.api.Project

internal fun Project.markAsProjectType(type: ProjectType) {
	this.setType(type)
	ProjectStructureEnforcementPlugin.applyTo(project = this)
}

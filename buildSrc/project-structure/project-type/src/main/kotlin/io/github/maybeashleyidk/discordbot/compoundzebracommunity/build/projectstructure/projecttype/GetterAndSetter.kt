package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.internal.ProjectTypePlugin
import org.gradle.api.Project

public fun Project.setType(type: ProjectType) {
	ProjectTypePlugin.applyTo(project = this, type)
}

public fun Project.getTypeOrNull(): ProjectType? {
	return ProjectTypePlugin.getTypeOrNullFrom(project = this)
}

public fun Project.requireType(): ProjectType {
	val type: ProjectType? = this.getTypeOrNull()

	checkNotNull(type) {
		"The $this has no type"
	}

	return type
}

public fun Project.hasType(): Boolean {
	return (this.getTypeOrNull() != null)
}

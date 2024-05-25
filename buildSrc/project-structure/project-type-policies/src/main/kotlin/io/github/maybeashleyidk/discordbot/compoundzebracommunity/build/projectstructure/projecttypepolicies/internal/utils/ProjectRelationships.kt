package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils

import org.gradle.api.Project

internal fun areSiblings(a: Project, b: Project): Boolean {
	val aParent: Project = a.parent
		?: return false

	val bParent: Project = b.parent
		?: return false

	return (aParent == bParent)
}

internal fun Project.isParentOf(potentialChildProject: Project): Boolean {
	return (this == potentialChildProject.parent)
}

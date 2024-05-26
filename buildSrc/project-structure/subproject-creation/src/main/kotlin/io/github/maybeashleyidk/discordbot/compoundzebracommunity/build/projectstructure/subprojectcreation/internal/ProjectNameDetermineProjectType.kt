package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.isAllowedBy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.isReservedBy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.selectPolicy

internal fun ProjectName.determineProjectTypeOrNull(): ProjectType? {
	return ProjectType.values()
		.singleOrNull { projectType: ProjectType ->
			projectType.isSuitableForProjectNamed(this)
		}
}

private fun ProjectType.isSuitableForProjectNamed(name: ProjectName): Boolean {
	val namePolicy: ProjectNamePolicy = this.selectPolicy().namePolicy

	return (name.isAllowedBy(namePolicy) && !(name.isReservedByProjectTypeOtherThan(this)))
}

private fun ProjectName.isReservedByProjectTypeOtherThan(type: ProjectType): Boolean {
	return ProjectType.values()
		.filter { it != type }
		.any { otherProjectType: ProjectType ->
			val otherNamePolicy: ProjectNamePolicy = otherProjectType.selectPolicy().namePolicy

			this.isReservedBy(otherNamePolicy)
		}
}

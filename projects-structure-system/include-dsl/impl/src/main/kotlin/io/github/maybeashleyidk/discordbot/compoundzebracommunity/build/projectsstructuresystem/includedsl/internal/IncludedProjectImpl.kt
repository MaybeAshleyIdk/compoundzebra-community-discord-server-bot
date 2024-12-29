package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.IncludeDsl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails.Composite
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails.Namespace
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails.Service
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails.Standalone

internal class IncludedProjectImpl(inclusionInfoDetails: ProjectInclusionInfoDetails) : IncludeDsl.IncludedProject {

	private var finalized: Boolean = false

	var inclusionInfoDetails: ProjectInclusionInfoDetails = inclusionInfoDetails
		private set(value) {
			check(!(this.finalized)) {
				"This IncludedProject is finalized and cannot be changed anymore"
			}

			field = value
		}

	override fun inDirectory(dir: String): IncludeDsl.IncludedProject {
		this.inclusionInfoDetails = this.inclusionInfoDetails.withDirectoryName(dir)
		return this
	}

	@JvmName("finalizeProject")
	fun finalize() {
		this.finalized = true
	}
}

private fun ProjectInclusionInfoDetails.withDirectoryName(directoryName: String): ProjectInclusionInfoDetails {
	return when (this) {
		is Standalone -> this.copy(directoryName = directoryName)
		is Namespace -> this.copy(directoryName = directoryName)
		is Composite -> this.copy(directoryName = directoryName)
		is Service -> this.copy(directoryName = directoryName)
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.internal.IncludeDslImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName

public fun collectTopLevelProjectInclusionInfoDetailsMapFromIncludeDsl(
	includeDslBlock: IncludeDsl.() -> Unit,
): Map<ProjectName, ProjectInclusionInfoDetails> {
	val dslImpl = IncludeDslImpl()

	dslImpl.finalizeAfter { dslImpl.includeDslBlock() }

	return dslImpl.includedProjects
}

@file:JvmName(
	"io_github_maybeashleyidk_discordbot_compoundzebracommunity_build_projectsstructuresystem_settingsinclude_SettingsIncludeKt",
)

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.IncludeDsl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.collectTopLevelProjectInclusionInfoDetailsMapFromIncludeDsl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal.ProjectEnforcementIsolatedAction
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal.includeRecursivelyAtTopLevel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal.toProjectDetailsMap
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo.ProjectDetails
import org.gradle.api.initialization.Settings

public fun Settings.include(include: IncludeDsl.() -> Unit) {
	val topLevelProjectInclusionInfoDetailsMap: Map<ProjectName, ProjectInclusionInfoDetails> =
		collectTopLevelProjectInclusionInfoDetailsMapFromIncludeDsl(include)

	this.includeRecursivelyAtTopLevel(topLevelProjectInclusionInfoDetailsMap)

	val topLevelProjectDetailsMap: Map<ProjectName, ProjectDetails.NonRoot> =
		topLevelProjectInclusionInfoDetailsMap.toProjectDetailsMap()
	val rootProjectDetails = ProjectDetails.Root(topLevelProjectDetailsMap)
	this.gradle.lifecycle.afterProject(ProjectEnforcementIsolatedAction(rootProjectDetails))
}
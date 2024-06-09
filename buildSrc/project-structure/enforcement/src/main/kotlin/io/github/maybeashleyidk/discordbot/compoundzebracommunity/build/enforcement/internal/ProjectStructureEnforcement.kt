package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import org.gradle.api.Project

internal object ProjectStructureEnforcement {

	fun enforceProjectStructure(projectTree: Tree<Project>) {
		ProjectNamesEnforcement.enforceProjectsNames(projectTree)
		ProjectPluginsEnforcement.enforceProjectsPlugins(projectTree)
		ProjectSourceCodeEnforcement.enforceProjectsSourceCode(projectTree)
		ProjectDependenciesEnforcement.enforceProjectsDependencies(projectTree)
		ProjectChildrenEnforcement.enforceProjectsChildren(projectTree)
	}
}

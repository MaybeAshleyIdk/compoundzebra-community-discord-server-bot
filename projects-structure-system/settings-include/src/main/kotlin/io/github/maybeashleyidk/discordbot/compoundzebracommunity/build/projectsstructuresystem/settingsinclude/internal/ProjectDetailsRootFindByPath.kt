package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectpath.ProjectPath
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.structuredprojectinfo.ProjectDetails

internal fun ProjectDetails.Root.findByPath(projectPath: ProjectPath): ProjectDetails? {
	val splitFirstResult: ProjectPath.SplitFirstResult = projectPath.splitFirst()
		?: return this

	return this.topLevelProjects[splitFirstResult.firstName]?.findByPath(splitFirstResult.restPath)
}

private fun ProjectDetails.NonRoot.findByPath(projectPath: ProjectPath): ProjectDetails.NonRoot? {
	val splitFirstResult: ProjectPath.SplitFirstResult = projectPath.splitFirst()
		?: return this

	return when (this) {
		is ProjectDetails.Standalone -> null
		is ProjectDetails.Namespace -> this.findByPath(splitFirstResult)
		is ProjectDetails.Composite -> this.findByPath(splitFirstResult)
		is ProjectDetails.Service -> this.findByPath(splitFirstResult)
	}
}

private fun ProjectDetails.Namespace.findByPath(
	splitFirstResult: ProjectPath.SplitFirstResult,
): ProjectDetails.NonRoot? {
	@Suppress("ImplicitThis")
	return this.childProjects[splitFirstResult.firstName]?.findByPath(splitFirstResult.restPath)
}

private fun ProjectDetails.Composite.findByPath(
	splitFirstResult: ProjectPath.SplitFirstResult,
): ProjectDetails.NonRoot? {
	@Suppress("ImplicitThis")
	return this.details.childProjects[splitFirstResult.firstName]?.findByPath(splitFirstResult.restPath)
}

private fun ProjectDetails.Service.findByPath(splitFirstResult: ProjectPath.SplitFirstResult): ProjectDetails.NonRoot? {
	if (splitFirstResult.firstName == SERVICE_INTERFACE_PROJECT_NAME) {
		return ProjectDetails.Standalone.ServiceInterface
	}

	val implementationName: ServiceImplementationName =
		ServiceImplementationName.fromFullProjectName(splitFirstResult.firstName) ?: return null

	@Suppress("ImplicitThis")
	return this.implementationProjects[implementationName]?.findByPath(splitFirstResult.restPath)
}

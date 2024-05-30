package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.projectname.isNotLegal
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings

internal class IncludeDslImpl private constructor(
	private val settings: Settings,
	private val parentProject: ProjectInfo?,
) : IncludeDsl {

	companion object {

		fun createForTopLevel(settings: Settings): IncludeDsl {
			return IncludeDslImpl(settings, parentProject = null)
		}
	}

	override operator fun String.invoke(childrenInclude: IncludeDsl.() -> Unit) {
		val projectName: ProjectName? = ProjectName.ofStringOrNull(this@invoke)

		checkNotNull(projectName) {
			"Invalid project name \"$projectName\""
		}

		this@IncludeDslImpl.include(requestedNewProjectName = projectName, childrenIncludeDslBlock = childrenInclude)
	}

	override operator fun String.invoke() {
		val projectName: ProjectName? = ProjectName.ofStringOrNull(this@invoke)

		checkNotNull(projectName) {
			"Invalid project name \"$projectName\""
		}

		this@IncludeDslImpl.include(requestedNewProjectName = projectName, childrenIncludeDslBlock = {})
	}

	private fun include(requestedNewProjectName: ProjectName, childrenIncludeDslBlock: IncludeDsl.() -> Unit) {
		val newProjectInfo =
			ProjectInfo(
				parent = this.parentProject,
				name = this.makeProjectName(requestedNewProjectName),
			)

		val childrenIncludeDsl: IncludeDsl = IncludeDslImpl(this.settings, parentProject = newProjectInfo)
		with(childrenIncludeDsl, childrenIncludeDslBlock)

		val newProjectPath: String = newProjectInfo.buildPath()

		this.settings.include(newProjectPath)

		if (requestedNewProjectName.isNotLegal()) {
			val newProject: ProjectDescriptor = this.settings.project(newProjectPath)
			newProject.projectDir = newProject.projectDir.parentFile.resolve(requestedNewProjectName.toString())
		}
	}

	private fun makeProjectName(requestedNewProjectName: ProjectName): ProjectName {
		if (requestedNewProjectName.isLegal()) {
			return requestedNewProjectName
		}

		checkNotNull(this.parentProject) {
			"A top-level project cannot be named \"$requestedNewProjectName\""
		}

		val parentProjectNameSingularStr: String = this.parentProject.name.toString().removeSuffix("s")
		val projectNameStr = "$parentProjectNameSingularStr-$requestedNewProjectName"

		return ProjectName.ofString(projectNameStr)
	}
}

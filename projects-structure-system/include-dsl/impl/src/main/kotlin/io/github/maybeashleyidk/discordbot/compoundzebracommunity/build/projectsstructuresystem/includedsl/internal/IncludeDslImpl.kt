package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.Composite
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.IncludeDsl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.Namespace
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.Service
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName

internal class IncludeDslImpl : IncludeDsl {

	private var finalized: Boolean = false

	private val mutableIncludedProjects: MutableMap<ProjectName, IncludedProjectImpl> = LinkedHashMap()

	val includedProjects: Map<ProjectName, ProjectInclusionInfoDetails>
		get() {
			return this.mutableIncludedProjects
				.mapValues { (_: ProjectName, includedProject: IncludedProjectImpl) ->
					includedProject.inclusionInfoDetails
				}
		}

	override fun String.invoke(): IncludedProjectImpl {
		val projectName: ProjectName = this@invoke.toProjectName()

		val inclusionInfoDetails = ProjectInclusionInfoDetails.Standalone(directoryName = null)

		return this@IncludeDslImpl.includeUniqueProject(projectName, inclusionInfoDetails)
	}

	override fun String.invoke(projectType: Namespace, include: IncludeDsl.() -> Unit): IncludedProjectImpl {
		val projectName: ProjectName = this@invoke.toProjectName()

		val nestedDsl = IncludeDslImpl()
		nestedDsl.finalizeAfter { nestedDsl.include() }

		val inclusionInfoDetails = ProjectInclusionInfoDetails
			.Namespace(
				directoryName = null,
				childProjects = nestedDsl.includedProjects,
			)

		return this@IncludeDslImpl.includeUniqueProject(projectName, inclusionInfoDetails)
	}

	override fun String.invoke(
		projectType: Composite,
		include: IncludeDsl.() -> IncludeDsl.IncludedProject,
	): IncludedProjectImpl {
		val projectName: ProjectName = this@invoke.toProjectName()

		val nestedDsl = IncludeDslImpl()
		val publicIncludedProject: IncludeDsl.IncludedProject = nestedDsl.finalizeAfter { nestedDsl.include() }
		val publicIncludedProjectName: ProjectName = nestedDsl.getNameOfIncludedProject(publicIncludedProject)

		val inclusionInfoDetails = ProjectInclusionInfoDetails
			.Composite(
				directoryName = null,
				childProjects = nestedDsl.includedProjects,
				publicChildProjectName = publicIncludedProjectName,
			)

		return this@IncludeDslImpl.includeUniqueProject(projectName, inclusionInfoDetails)
	}

	// region service

	override fun String.invoke(projectType: Service): IncludedProjectImpl {
		val projectName: ProjectName = this@invoke.toProjectName()

		val inclusionInfoDetails = ProjectInclusionInfoDetails
			.Service(
				directoryName = null,
				implementationProjects = mapOf(
					ServiceImplementationName.None to ProjectInclusionInfoDetails.Standalone(directoryName = null),
				),
			)

		return this@IncludeDslImpl.includeUniqueProject(projectName, inclusionInfoDetails)
	}

	override fun String.invoke(projectType: Service, impl: String): IncludedProjectImpl {
		val projectName: ProjectName = this@invoke.toProjectName()

		val implementationName: ServiceImplementationName? = ServiceImplementationName.ofString(impl)
		checkNotNull(implementationName) {
			"Invalid implementation name \"$impl\""
		}

		val inclusionInfoDetails = ProjectInclusionInfoDetails
			.Service(
				directoryName = null,
				implementationProjects = mapOf(
					implementationName to ProjectInclusionInfoDetails.Standalone(directoryName = null),
				),
			)

		return this@IncludeDslImpl.includeUniqueProject(projectName, inclusionInfoDetails)
	}

	override fun String.invoke(
		projectType: Service,
		implInclude: IncludeDsl.() -> IncludeDsl.IncludedProject,
	): IncludedProjectImpl {
		val projectName: ProjectName = this@invoke.toProjectName()

		val nestedDsl = IncludeDslImpl()
		val publicIncludedProject: IncludeDsl.IncludedProject = nestedDsl.finalizeAfter { nestedDsl.implInclude() }
		val publicIncludedProjectName: ProjectName = nestedDsl.getNameOfIncludedProject(publicIncludedProject)

		val implementationProjectInclusionInfoDetails = ProjectInclusionInfoDetails
			.Composite(
				directoryName = null,
				childProjects = nestedDsl.includedProjects,
				publicChildProjectName = publicIncludedProjectName,
			)

		val inclusionInfoDetails = ProjectInclusionInfoDetails
			.Service(
				directoryName = null,
				implementationProjects = mapOf(
					ServiceImplementationName.None to implementationProjectInclusionInfoDetails,
				),
			)

		return this@IncludeDslImpl.includeUniqueProject(projectName, inclusionInfoDetails)
	}

	// endregion

	@JvmName("finalizeDsl")
	fun finalize() {
		this.finalized = true

		for (includedProject: IncludedProjectImpl in this.mutableIncludedProjects.values) {
			includedProject.finalize()
		}
	}

	inline fun <R> finalizeAfter(crossinline action: () -> R): R {
		try {
			return action()
		} finally {
			this.finalize()
		}
	}

	private fun getNameOfIncludedProject(includedProject: IncludeDsl.IncludedProject): ProjectName {
		return this.mutableIncludedProjects.entries
			.first { (_: ProjectName, includedProjectImpl: IncludedProjectImpl) ->
				includedProjectImpl == includedProject
			}
			.key
	}

	private fun includeUniqueProject(
		projectName: ProjectName,
		inclusionInfoDetails: ProjectInclusionInfoDetails,
	): IncludedProjectImpl {
		val includedProject = IncludedProjectImpl(inclusionInfoDetails)

		check(!(this.finalized)) {
			"This IncludeDsl is finalized and cannot be changed anymore"
		}

		this.mutableIncludedProjects
			.merge(projectName, includedProject) { _, _ ->
				error("Project included with duplicate name \"$projectName\"")
			}

		return includedProject
	}
}

private fun String.toProjectName(): ProjectName {
	val projectName: ProjectName? = ProjectName.ofString(nameString = this)

	requireNotNull(projectName) {
		"Invalid project name \"${this}\""
	}

	return projectName
}

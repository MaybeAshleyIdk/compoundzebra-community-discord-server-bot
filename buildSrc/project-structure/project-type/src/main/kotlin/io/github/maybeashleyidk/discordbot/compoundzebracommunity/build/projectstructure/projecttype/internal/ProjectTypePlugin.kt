package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.hasPlugin

internal class ProjectTypePlugin : Plugin<Project> {

	interface ProjectTypeExtension {

		val type: Property<ProjectType>
	}

	companion object {

		fun applyTo(project: Project, type: ProjectType) {
			require(!(project.hasProjectTypePlugin()))

			project.apply<ProjectTypePlugin>()

			project.extensions.configure<ProjectTypeExtension> {
				this@configure.type.set(type)
			}
		}

		fun getTypeOrNullFrom(project: Project): ProjectType? {
			if (!(project.hasProjectTypePlugin())) {
				return null
			}

			val extension: ProjectTypeExtension = project.extensions.getByType<ProjectTypeExtension>()
			return extension.type.get()
		}
	}

	override fun apply(project: Project) {
		project.extensions.create<ProjectTypeExtension>(name = "_projectType")
	}
}

private fun Project.hasProjectTypePlugin(): Boolean {
	return this.plugins.hasPlugin(ProjectTypePlugin::class)
}

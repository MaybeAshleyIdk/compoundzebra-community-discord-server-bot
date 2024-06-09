package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal.ProjectStructureEnforcement
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal.toTree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.hasType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.filterSubNodes
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.hasPlugin

public class ProjectStructureEnforcementPlugin : Plugin<Project> {

	public companion object {

		public fun applyTo(project: Project) {
			project.pluginManager.apply(ProjectStructureEnforcementPlugin::class)
		}
	}

	override fun apply(project: Project) {
		project.afterEvaluate {
			// This doesn't really make sense, but it works and does exactly what I want.
			this@afterEvaluate.evaluationDependsOnChildren()

			this@ProjectStructureEnforcementPlugin.afterEvaluate(project = this@afterEvaluate)
		}
	}

	private fun afterEvaluate(project: Project) {
		// Delegating the project structure enforcement to the top-most plugin instance.
		val parent: Project? = project.parent
		if ((parent != null) && parent.plugins.hasPlugin(ProjectStructureEnforcementPlugin::class)) {
			return
		}

		check(project.hasType()) {
			"The $project has no type"
		}

		val projectTree: Tree<Project> = project.toTree()
			.filterSubNodes(Project::hasType)

		ProjectStructureEnforcement.enforceProjectStructure(projectTree)
	}
}

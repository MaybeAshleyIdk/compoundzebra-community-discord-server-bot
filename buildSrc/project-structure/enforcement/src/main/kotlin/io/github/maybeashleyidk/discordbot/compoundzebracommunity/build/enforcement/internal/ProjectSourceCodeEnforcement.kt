package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.SourceCodePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.requireType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.selectPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.flattenDepth
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import java.io.File

internal object ProjectSourceCodeEnforcement {

	fun enforceProjectsSourceCode(projectTree: Tree<Project>) {
		for (project: Project in projectTree.flattenDepth()) {
			this.enforceProjectSourceCodeFor(project)
		}
	}

	private fun enforceProjectSourceCodeFor(project: Project) {
		val sourceCodePolicy: SourceCodePolicy = project.requireType().selectPolicy().sourceCodePolicy

		when (sourceCodePolicy) {
			SourceCodePolicy.NONE -> this.enforceNone(project)
			SourceCodePolicy.KOTLIN -> this.enforceKotlin(project)
		}
	}

	private fun enforceNone(project: Project) {
		val sourceSets: SourceSetContainer = project.getSourceSetsOrNull()
			?: return

		for (sourceSet: SourceSet in sourceSets) {
			check(sourceSet.allSource.isEmpty) {
				"The $sourceSet in $project is not empty"
			}
		}
	}

	private fun enforceKotlin(project: Project) {
		val sourceSets: SourceSetContainer? = project.getSourceSetsOrNull()
		checkNotNull(sourceSets) {
			"No source sets in $project"
		}

		val mainSourceSet: SourceSet? = sourceSets.findByName("main")
		checkNotNull(mainSourceSet) {
			"No main source set in $project"
		}

		val hasKotlinFiles: Boolean = mainSourceSet.allSource
			.any { sourceFile: File ->
				sourceFile.name.endsWith(".kt")
			}

		if (!hasKotlinFiles) {
			project.logger.warn("The $mainSourceSet in $project contains no Kotlin source files")
		}
	}
}

private fun Project.getSourceSetsOrNull(): SourceSetContainer? {
	return this.extensions.findByName<SourceSetContainer>("sourceSets")
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
private inline fun <reified T : Any> ExtensionContainer.findByName(name: String): T? {
	val extension: Any = this.findByName(name)
		?: return null

	check(extension is T) {
		"Element '$name' of type '${extension::class.java.name}' from container '$this' cannot be cast to " +
			"'${T::class.qualifiedName}'."
	}

	return extension
}

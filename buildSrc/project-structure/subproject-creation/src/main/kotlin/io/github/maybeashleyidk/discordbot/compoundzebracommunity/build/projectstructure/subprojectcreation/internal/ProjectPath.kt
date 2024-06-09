package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.ProjectName
import org.gradle.api.file.Directory
import java.io.File

@JvmInline
internal value class ProjectPath private constructor(private val names: List<ProjectName>) {

	companion object {

		fun ofStringOrNull(pathString: String): ProjectPath? {
			val names: List<ProjectName> = pathString
				.replace('/', ':')
				.replace(File.separatorChar, ':')
				.removePrefix(":")
				.split(":")
				.map(ProjectName::ofStringOrNull)
				.nonNullOrNull()
				?.ifEmpty { null }
				?: return null

			return ProjectPath(names)
		}
	}

	init {
		require(this.names.isNotEmpty()) {
			"The project path must consist of at least one project name"
		}
	}

	val projectNames: Iterable<ProjectName>
		get() {
			return this.names
		}

	fun countNames(): Int {
		return this.names.size
	}

	fun getFinalName(): ProjectName {
		return this.names.last()
	}

	fun startsWith(prefix: ProjectPath): Boolean {
		return this.names.startsWith(prefix.names)
	}

	fun removePrefix(prefix: ProjectPath): ProjectPath? {
		val thisNamesWithoutPrefix: List<ProjectName> = this.names.removePrefix(prefix.names)

		if (thisNamesWithoutPrefix.isEmpty()) {
			return null
		}

		return ProjectPath(thisNamesWithoutPrefix)
	}

	override fun toString(): String {
		return this.names.joinToString(prefix = ":", separator = ":")
	}
}

internal fun Directory.dir(projectPath: ProjectPath): Directory {
	return projectPath.projectNames
		.fold(initial = this, Directory::dir)
}

private fun Directory.dir(projectName: ProjectName): Directory {
	return this.dir(projectName.toString())
}

private fun <T : Any> List<T?>.nonNullOrNull(): List<T>? {
	if (this.any { element: T? -> (element == null) }) {
		return null
	}

	@Suppress("UNCHECKED_CAST")
	return (this as List<T>)
}

private fun <E> List<E>.removePrefix(prefix: List<E>): List<E> {
	if (!(this.startsWith(prefix))) {
		return this
	}

	return this.slice((prefix.lastIndex + 1) until this.size)
}

private fun <E> List<E>.startsWith(prefix: List<E>): Boolean {
	if (prefix.size > this.size) {
		return false
	}

	repeat(prefix.size) { i: Int ->
		val thisElement: E = this[i]
		val prefixElement: E = prefix[i]

		if (thisElement != prefixElement) {
			return@startsWith false
		}
	}

	return true
}

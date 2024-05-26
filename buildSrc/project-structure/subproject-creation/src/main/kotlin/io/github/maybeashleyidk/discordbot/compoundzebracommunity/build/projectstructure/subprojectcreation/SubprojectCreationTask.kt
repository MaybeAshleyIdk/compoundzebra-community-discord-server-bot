package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.ProjectPath
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.determineProjectTypeOrNull
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.dir
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.packages.FullyQualifiedPackageName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.packages.PackageName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.packages.dir
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.utils.mkdirs
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.work.DisableCachingByDefault
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@DisableCachingByDefault
public abstract class SubprojectCreationTask : DefaultTask() {

	init {
		this.description = "Creates a new subproject from a template"
	}

	@get:Inject
	internal abstract val projectLayout: ProjectLayout

	// region properties

	@get:Input
	@get:Option(option = "project-path", description = "The Gradle project path of the new subproject to create")
	public abstract val projectPath: Property<String>

	@get:Input
	@get:Optional
	@get:Option(
		option = "project-type",
		description = "The project type of the new subproject. " +
			"May be omitted if the type can be inferred from the project name",
	)
	public abstract val projectType: Property<ProjectType>

	@get:Input
	public abstract val basePackageName: Property<String>

	@get:Input
	public abstract val packageIgnoredProjectPathPrefixes: ListProperty<String>

	// endregion

	@TaskAction
	internal fun createSubproject() {
		this.createSubprojectDirectory()
		this.createGitignoreFile()
		this.createGradleBuildFile()
		this.createPackageDirectory()

		this.logger.lifecycle("Add the following to your `settings.gradle.kts` file:")
		println(
			"""
			include(
				"${this.getSubprojectPath()}",
			)
			""".trimIndent(),
		)
	}

	// region

	private fun createSubprojectDirectory() {
		val subprojectDirectory: Directory = this.getSubprojectDirectory()
		subprojectDirectory.mkdirs()

		this.logger.lifecycle("Created the directory '$subprojectDirectory'")
	}

	private fun createGitignoreFile() {
		val subprojectDirectory: Directory = this.getSubprojectDirectory()
		val gitignoreFile: RegularFile = subprojectDirectory.file(".gitignore")

		gitignoreFile.asFile
			.writeText("/build/\n")

		this.logger.lifecycle("Created the file '$gitignoreFile'")
	}

	private fun createGradleBuildFile() {
		val subprojectDirectory: Directory = this.getSubprojectDirectory()

		val gradleBuildFile: RegularFile = subprojectDirectory.file("build.gradle.kts")

		this.openGradleBuildFileTemplateStream()
			.use { gradleBuildFileTemplateStream: InputStream ->
				gradleBuildFile.asFile.outputStream()
					.use { gradleBuildFileStream: FileOutputStream ->
						gradleBuildFileTemplateStream.transferTo(gradleBuildFileStream)
					}
			}
	}

	private fun createPackageDirectory() {
		if (!(this.getSubprojectType().hasSourceCode())) {
			return
		}

		val packageDirectory: Directory = this.getPackageDirectory()
		packageDirectory.mkdirs()

		this.logger.lifecycle("Created the directory '$packageDirectory'")
	}

	// endregion

	private fun getPackageDirectory(): Directory {
		val basePackageDirectory: Directory = this.getBasePackageDirectory()
		val subprojectPath: ProjectPath = this.getSubprojectPath()

		val packageIgnoredProjectPathPrefix: ProjectPath? = this.getPackageIgnoredProjectPathPrefixes()
			.sortedWith { a: ProjectPath, b: ProjectPath ->
				a.countNames() - b.countNames()
			}
			.firstOrNull(subprojectPath::startsWith)

		val subprojectPathWithoutPackageIgnoredPrefix: ProjectPath =
			if (packageIgnoredProjectPathPrefix != null) {
				val subprojectPathWithoutPackageIgnoredPrefix: ProjectPath? =
					subprojectPath.removePrefix(packageIgnoredProjectPathPrefix)

				checkNotNull(subprojectPathWithoutPackageIgnoredPrefix) {
					"Removing the prefix $packageIgnoredProjectPathPrefix from $subprojectPath would lead to " +
						"an empty Gradle project path"
				}

				subprojectPathWithoutPackageIgnoredPrefix
			} else {
				subprojectPath
			}

		return basePackageDirectory.dir(subprojectPathWithoutPackageIgnoredPrefix.toFullyQualifiedPackageName())
	}

	private fun getBasePackageDirectory(): Directory {
		val subprojectDirectory: Directory = this.getSubprojectDirectory()
		val baseFullyQualifiedPackageName: FullyQualifiedPackageName = this.getBaseFullyQualifiedPackageName()

		val kotlinMainSourceDirectory: Directory = subprojectDirectory
			.dir("src").dir("main").dir("kotlin")

		return kotlinMainSourceDirectory.dir(baseFullyQualifiedPackageName)
	}

	private fun getSubprojectDirectory(): Directory {
		val projectDirectory: Directory = this.projectLayout.projectDirectory
		val subprojectPath: ProjectPath = this.getSubprojectPath()

		return projectDirectory.dir(subprojectPath)
	}

	private fun openGradleBuildFileTemplateStream(): InputStream {
		val subprojectType: ProjectType = this.getSubprojectType()

		val gradleBuildFileTemplateResourceBaseName: String =
			when (subprojectType) {
				ProjectType.STANDALONE -> "standalone"
				ProjectType.NAMESPACE -> "namespace"
				ProjectType.COMPOSITE -> "composite"
				ProjectType.SERVICE -> "service"
				ProjectType.SERVICE_INTERFACE -> "service-interface"
				ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> "service-implementation.standalone"
				ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> "service-implementation.composite"
				ProjectType.SERVICE_WIRING -> "service-wiring"
			}

		val gradleBuildFileTemplateResourceName = "gradlebuildfiles/$gradleBuildFileTemplateResourceBaseName.gradle.kts"

		return checkNotNull(this.javaClass.getResourceAsStream(gradleBuildFileTemplateResourceName))
			.buffered()
	}

	// region property getters

	private fun getPackageIgnoredProjectPathPrefixes(): List<ProjectPath> {
		val packageIgnoredProjectPathPrefixStrings: List<String> = this.packageIgnoredProjectPathPrefixes.get()

		return packageIgnoredProjectPathPrefixStrings
			.map { packageIgnoredProjectPathPrefixStr: String ->
				val packageIgnoredProjectPathPrefix: ProjectPath? =
					ProjectPath.ofStringOrNull(packageIgnoredProjectPathPrefixStr)

				checkNotNull(packageIgnoredProjectPathPrefix) {
					"Invalid Gradle project path \"$packageIgnoredProjectPathPrefixStr\""
				}

				packageIgnoredProjectPathPrefix
			}
	}

	private fun getBaseFullyQualifiedPackageName(): FullyQualifiedPackageName {
		val baseFullyQualifiedPackageNameStr: String = this.basePackageName.get()

		val baseFullyQualifiedPackageName: FullyQualifiedPackageName? =
			FullyQualifiedPackageName.ofStringOrNull(baseFullyQualifiedPackageNameStr)

		checkNotNull(baseFullyQualifiedPackageName) {
			"Invalid fully-qualified package name \"$baseFullyQualifiedPackageNameStr\""
		}

		return baseFullyQualifiedPackageName
	}

	private fun getSubprojectType(): ProjectType {
		return this.projectType.orNull
			?: this.getSubprojectPath().getFinalName().determineProjectTypeOrNull()
			?: error(
				"No project type has been configured and it also could not have been inferred from " +
					"the subproject name",
			)
	}

	private fun getSubprojectPath(): ProjectPath {
		val subprojectPathStr: String = this.projectPath.get()

		val subprojectPath: ProjectPath? = ProjectPath.ofStringOrNull(subprojectPathStr)

		checkNotNull(subprojectPath) {
			"Invalid Gradle project path \"$subprojectPathStr\""
		}

		return subprojectPath
	}

	// endregion
}

private fun ProjectType.hasSourceCode(): Boolean {
	return when (this) {
		ProjectType.STANDALONE -> true
		ProjectType.NAMESPACE -> false
		ProjectType.COMPOSITE -> false
		ProjectType.SERVICE -> false
		ProjectType.SERVICE_INTERFACE -> true
		ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> true
		ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> false
		ProjectType.SERVICE_WIRING -> true
	}
}

private fun ProjectPath.toFullyQualifiedPackageName(): FullyQualifiedPackageName {
	val packageNames: List<PackageName> = this.projectNames
		.asSequence()
		.map(ProjectName::toPackageName)
		.toList()

	return FullyQualifiedPackageName.ofPackageNames(packageNames)
}

private fun ProjectName.toPackageName(): PackageName {
	val packageNameStr: String = this.toString()
		.replace("-", "")

	val packageName: PackageName? = PackageName.ofStringOrNull(packageNameStr)

	checkNotNull(packageName) {
		"Invalid package name: \"$packageNameStr\""
	}

	return packageName
}

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.io.FileInputStream
import java.io.OutputStream
import java.util.zip.GZIPOutputStream

plugins {
	application
	kotlin("jvm")

	alias(libs.plugins.ktlint)
	alias(libs.plugins.shadow)
}

group = "io.github.maybeashleyidk"
version = "0.1.0-indev08"

application {
	applicationName = "czd-bot"
	mainClass = "io.github.maybeashleyidk.discordbot.compoundzebracommunity.Main"
}

ktlint {
	version = libs.versions.ktlint
	installGitPreCommitHookBeforeBuild = true
}

dependencies {
	implementation(project(":bot"))
}

// region executable file tasks

val createExecutable: TaskProvider<Task> by tasks.registering {
	// <https://skife.org/java/unix/2011/06/20/really_executable_jars.html>

	group = "distribution"
	description = "Creates an executable file of the bot"

	dependsOn(tasks.shadowJar)

	val jarExecScriptFile: RegularFile = project.rootProject
		.layout.projectDirectory.file("jar-exec-script.sh")

	val shadowJarFileProvider: Provider<File> = tasks.shadowJar
		.map { shadowJarTask: ShadowJar ->
			shadowJarTask.outputs.files.singleFile
		}

	val executableFileProvider: Provider<RegularFile> = project.layout.buildDirectory
		.dir("distributions")
		.map { distDir: Directory ->
			distDir.file("${project.application.applicationName}-${project.version}")
		}

	inputs.files(jarExecScriptFile, shadowJarFileProvider)
	outputs.file(executableFileProvider)

	doLast {
		val executableFile: File = executableFileProvider.get().asFile

		executableFile
			.outputStream()
			.buffered()
			.use { executableFileStream: OutputStream ->
				jarExecScriptFile.asFile
					.inputStream()
					.use { jarExecScriptFileStream: FileInputStream ->
						jarExecScriptFileStream.transferTo(executableFileStream)
					}

				shadowJarFileProvider.get()
					.inputStream()
					.use { shadowJarFileStream: FileInputStream ->
						shadowJarFileStream.transferTo(executableFileStream)
					}
			}

		executableFile.setExecutable(true)
	}
}

val createGzippedExecutable: TaskProvider<Task> by tasks.registering {
	group = "distribution"
	description = "Creates a gzipped executable file of the bot in the root project's directory"

	dependsOn(createExecutable)

	val executableFileProvider: Provider<File> = createExecutable
		.map { executableCreationTask: Task ->
			executableCreationTask.outputs.files.singleFile
		}

	val gzippedExecutableFileProvider: Provider<RegularFile> = project.rootProject
		.layout.projectDirectory.file(
			executableFileProvider.map { executableFile: File ->
				"${executableFile.name}.gz"
			},
		)

	inputs.file(executableFileProvider)
	outputs.file(gzippedExecutableFileProvider)

	doLast {
		GZIPOutputStream(outputs.files.singleFile.outputStream().buffered())
			.use { outputFileStream: OutputStream ->
				inputs.files.singleFile.inputStream()
					.use { inputFileStream: FileInputStream ->
						inputFileStream.transferTo(outputFileStream)
					}
			}
	}
}

tasks.clean {
	doLast {
		delete(createGzippedExecutable.get().outputs.files.singleFile)
	}
}

// endregion

// region broken tasks

// Gradle has issues because we have multiple modules with the same name ('api', 'impl' and 'wiring')
// the fix for that is to use shadow JAR

tasks.distTar {
	doFirst {
		val msg: String = "This task is broken because of duplicate module names." +
			" Use the ${tasks.shadowDistTar.get()} instead"
		error(msg)
	}
}

tasks.distZip {
	doFirst {
		val msg: String = "This task is broken because of duplicate module names." +
			" Use the ${tasks.shadowDistZip.get()} instead"
		error(msg)
	}
}

tasks.installDist {
	doFirst {
		val msg: String = "This task is broken because of duplicate module names." +
			" Use the ${tasks.installShadowDist.get()} instead"
		error(msg)
	}
}

// endregion

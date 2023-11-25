import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.OutputStream
import java.util.zip.GZIPOutputStream

plugins {
	StandaloneProject

	application
	kotlin("jvm")

	alias(libs.plugins.ktlint)
	alias(libs.plugins.shadow)
}

group = "io.github.maybeashleyidk"
version = "0.1.0-indev12"

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

tasks.shadowJar {
	minimize {
		// i dislike this very much, because with this, the module :main must have knowledge of
		// implementation-details modules.
		// might be that we won't need it anymore once we switch from JSON config to database?
		exclude(project(":bot:config-serialization:impl-json"))
	}
}

val createExecutable: TaskProvider<Task> by tasks.registering {
	// <https://skife.org/java/unix/2011/06/20/really_executable_jars.html>

	group = "distribution"
	description = "Creates an executable file of the bot"

	dependsOn(tasks.shadowJar)

	val jarExecScriptFile: RegularFile = project.layout.projectDirectory.file("jar-exec-script.sh")

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
tasks.assemble {
	dependsOn(createExecutable)
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
	delete(createGzippedExecutable.map(Task::getOutputs))
}

// endregion

tasks.run.configure {
	doFirst {
		workingDir = project.rootProject
			.layout.projectDirectory
			.dir("run").asFile
			.also(File::mkdirs)
	}

	doFirst {
		val environmentStr: String = System.getenv("CZD_BOT_ENVIRONMENT")
			.orEmpty()
			.ifEmpty { "dev" }

		environment("CZD_BOT_ENVIRONMENT", environmentStr)
	}

	doFirst {
		val botTokenFile: RegularFile = project
			.layout.projectDirectory
			.file("bot_token.txt")

		val tokenStr: String? = System.getenv("DISCORD_BOT_TOKEN")
			?.ifEmpty { null }
			?: try {
				botTokenFile.asFile.readText().trim()
			} catch (_: FileNotFoundException) {
				null
			}

		checkNotNull(tokenStr) {
			"To run the bot, the token must be supplied either via " +
				"the environment variable 'DISCORD_BOT_TOKEN' or " +
				"via the file '$botTokenFile' (environment variable has higher priority)"
		}

		environment("DISCORD_BOT_TOKEN", tokenStr)
	}
}

// these tasks are broken because we have multiple modules with the same name. (e.g.: "api", "impl" and "wiring")
// we don't need them anyway, so it's not a problem. simply disable them so that the task "build" doesn't fail
tasks.distTar { enabled = false }
tasks.distZip { enabled = false }
tasks.installDist { enabled = false }

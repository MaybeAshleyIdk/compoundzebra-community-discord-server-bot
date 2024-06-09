import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.gzip.GzipTask
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.reallyexecutablejar.ReallyExecutableJarTask
import java.io.FileNotFoundException

plugins {
	buildSrc.projectStructure.standalone

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
	implementation(projects.bot)
}

// region executable file tasks

val createExecutable: TaskProvider<ReallyExecutableJarTask> by tasks.registering(ReallyExecutableJarTask::class) {
	group = "distribution"
	description = "Creates an executable file of the bot"

	dependsOn(tasks.shadowJar)

	jarExecScriptFile = project.layout.projectDirectory.file("jar-exec-script.sh")

	inputJarFile = tasks.shadowJar
		.map { shadowJarTask: ShadowJar ->
			shadowJarTask.outputs.files.singleFile
		}

	outputJarFile = project.layout.buildDirectory
		.dir("distributions")
		.map { distDir: Directory ->
			distDir.file("${project.application.applicationName}-${project.version}")
		}
}
tasks.assemble {
	dependsOn(createExecutable)
}

val createGzippedExecutable: TaskProvider<out Task> by tasks.registering(GzipTask::class) {
	group = "distribution"
	description = "Creates a gzipped executable file of the bot in the root project's directory"

	dependsOn(createExecutable)

	inputFile = createExecutable.flatMap(ReallyExecutableJarTask::outputJarFile)
	outputDirectory(project.rootProject.layout.projectDirectory)
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

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.reallyexecutablejar

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream

// <https://skife.org/java/unix/2011/06/20/really_executable_jars.html>

public abstract class ReallyExecutableJarTask : DefaultTask() {

	@get:InputFile
	public abstract val jarExecScriptFile: RegularFileProperty

	@get:InputFile
	public abstract val inputJarFile: RegularFileProperty

	@get:OutputFile
	public abstract val outputJarFile: RegularFileProperty

	@TaskAction
	internal fun createExecutableJar() {
		val jarExecScriptFile: File = this.jarExecScriptFile.asFile.get()
		val inputJarFile: File = this.inputJarFile.asFile.get()
		val outputJarFile: File = this.outputJarFile.asFile.get()

		outputJarFile
			.outputStream()
			.buffered()
			.use { outputJarFileStream: OutputStream ->
				jarExecScriptFile
					.inputStream()
					.use { jarExecScriptFileStream: FileInputStream ->
						jarExecScriptFileStream.transferTo(outputJarFileStream)
					}

				inputJarFile
					.inputStream()
					.use { inputJarFileStream: FileInputStream ->
						inputJarFileStream.transferTo(outputJarFileStream)
					}
			}

		outputJarFile.setExecutable(true)
	}
}

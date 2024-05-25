package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.gzip

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.GZIPOutputStream

public abstract class GzipTask : DefaultTask() {

	init {
		this.description = "Compresses a single file using gzip"
	}

	@get:InputFile
	public abstract val inputFile: RegularFileProperty

	@get:OutputFile
	public abstract val outputFile: RegularFileProperty

	public fun outputDirectory(directory: Directory) {
		// The call .getLocationOnly() is important! Otherwise we get configuration errors.
		val outputFilenameProvider: Provider<String> = this.inputFile.locationOnly
			.map { inputFile: RegularFile ->
				"${inputFile.asFile.name}.gz"
			}

		this.outputFile.set(directory.file(outputFilenameProvider))
	}

	@TaskAction
	internal fun gzipFile() {
		val inputFile: File = this.inputFile.asFile.get()
		val outputFile: File = this.outputFile.asFile.get()

		outputFile
			.useGzipBufferedOutputStream { outputFileStream: OutputStream ->
				inputFile
					.inputStream()
					.use { inputFileStream: InputStream ->
						inputFileStream.transferTo(outputFileStream)
					}
			}
	}
}

private fun File.useGzipBufferedOutputStream(block: (OutputStream) -> Unit) {
	GZIPOutputStream(this.outputStream().buffered())
		.use(block)
}

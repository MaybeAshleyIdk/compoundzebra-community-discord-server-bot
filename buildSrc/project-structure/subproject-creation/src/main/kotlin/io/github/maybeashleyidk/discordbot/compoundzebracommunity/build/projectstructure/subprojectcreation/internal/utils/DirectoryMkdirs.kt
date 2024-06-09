package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.utils

import org.gradle.api.file.Directory
import java.io.File

internal fun Directory.mkdirs() {
	val file: File = this.asFile

	if (!(file.exists())) {
		check(file.mkdirs()) {
			"Could not create the directory $this"
		}

		return
	}

	if (file.isDirectory) {
		return
	}

	error("The path $this is not a directory")
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.impl

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.LogWriter

internal object StderrLogWriter : LogWriter() {

	override fun write(msg: String) {
		System.err.println(msg)
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import kotlin.system.exitProcess

internal enum class ExitStatus(internal val code: Int) {
	INVALID_ENVIRONMENT_TYPE(48),
	UNSET_OR_EMPTY_BOT_TOKEN_ENVIRONMENT_VARIABLE(49),
	INVALID_BOT_TOKEN_LENGTH(50),
}

internal fun exitProcess(status: ExitStatus): Nothing {
	exitProcess(status.code)
}

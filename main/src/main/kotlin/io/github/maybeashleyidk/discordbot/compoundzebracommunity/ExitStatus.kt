package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import kotlin.system.exitProcess

internal enum class ExitStatus(internal val code: Int) {
	UNSET_OR_EMPTY_BOT_ENVIRONMENT_TYPE_ENVIRONMENT_VARIABLE(48),
	INVALID_ENVIRONMENT_TYPE(49),
	UNSET_OR_EMPTY_BOT_TOKEN_ENVIRONMENT_VARIABLE(50),
	INVALID_BOT_TOKEN_LENGTH(51),
}

internal fun exitProcess(status: ExitStatus): Nothing {
	exitProcess(status.code)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
	@Json(name = "strings") val strings: LanguageStrings,
)

@JsonClass(generateAdapter = true)
data class LanguageStrings(
	@Json(name = "generic.invalidCommandName") val genericInvalidCommandName: String,
	@Json(name = "generic.unknownCommand") val genericUnknownCommand: String,
	@Json(name = "command.shutdown.response") val commandShutdownResponse: String,
	@Json(name = "command.shutdown.insufficientPermissions") val commandShutdownInsufficientPermissions: String,
	@Json(name = "command.shutdown.error") val commandShutdownError: String,
)

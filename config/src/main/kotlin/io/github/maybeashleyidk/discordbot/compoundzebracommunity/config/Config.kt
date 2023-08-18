package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class Config(
	@Json(name = "commandPrefix") val commandPrefix: String,
	@Json(name = "strings") val strings: LanguageStrings,
	@Json(name = "echoCommands") val echoCommandDefinitions: Map<String, EchoCommandDefinition>,
)

@JsonClass(generateAdapter = true)
public data class LanguageStrings(
	@Json(name = "generic.invalidCommandName") val genericInvalidCommandName: String,
	@Json(name = "generic.unknownCommand") val genericUnknownCommand: String,
	@Json(name = "command.shutdown.response") val commandShutdownResponse: String,
	@Json(name = "command.shutdown.insufficientPermissions") val commandShutdownInsufficientPermissions: String,
)

@JsonClass(generateAdapter = true)
public data class EchoCommandDefinition(
	@Json(name = "response") val responseMessage: String,
)

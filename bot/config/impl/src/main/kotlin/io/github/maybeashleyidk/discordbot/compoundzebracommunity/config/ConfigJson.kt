package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConfigJson(
	@Json(name = "strings") val strings: LanguageStringsJson,
	@Json(name = "botAdminUserIds") val botAdminUserIds: Set<String>?,
	@Json(name = "commandPrefix") val commandPrefix: String,
	@Json(name = "commands") val commands: Map<String, CommandDetailsJson>?,
)

@JsonClass(generateAdapter = true)
internal data class LanguageStringsJson(
	@Json(name = "generic.invalidCommandName") val genericInvalidCommandName: String,
	@Json(name = "generic.unknownCommand") val genericUnknownCommand: String,

	@Json(name = "poll.title") val pollTitle: String,
	@Json(name = "poll.option") val pollOption: String,
	@Json(name = "poll.closedMessage") val pollClosedMessage: String,
	@Json(name = "poll.action.close") val pollActionClose: String,

	@Json(name = "command.poll.missingDescription") val commandPollMissingDescription: String,
	@Json(name = "command.poll.lessThan2Options") val commandPollLessThan2Options: String,

	@Json(name = "command.querypoll.insufficientPermissions") val commandQuerypollInsufficientPermissions: String,
	@Json(name = "command.querypoll.missingId") val commandQuerypollMissingId: String,
	@Json(name = "command.querypoll.noSuchPollWithId") val commandQuerypollNoSuchPollWithId: String,

	@Json(name = "command.getconfig.insufficientPermissions") val commandGetconfigInsufficientPermissions: String,

	@Json(name = "command.shutdown.response") val commandShutdownResponse: String,
	@Json(name = "command.shutdown.insufficientPermissions") val commandShutdownInsufficientPermissions: String,
)

@JsonClass(generateAdapter = true)
internal data class CommandDetailsJson(
	@Json(name = "action") val action: ActionJson,
)

@JsonClass(generateAdapter = true)
internal data class ActionJson(
	@Json(name = "response") val responseMessage: String,
)

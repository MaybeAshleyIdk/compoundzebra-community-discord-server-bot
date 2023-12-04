package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConfigJson(
	@Json(name = "strings") val strings: LanguageStringsJson,
	@Json(name = "botAdminUserIds") val botAdminUserIds: Set<String>?,
	@Json(name = "commandPrefix") val commandPrefix: String,
	@Json(name = "commands") val commands: Map<String, CommandDetailsJson>?,
	@Json(name = "conditionalMessages") val conditionalMessages: Set<ConditionalMessageJson>?,
	@Json(name = "disabledCommandNames") val disabledCommandNames: Set<String>?,
)

@JsonClass(generateAdapter = true)
internal data class LanguageStringsJson
@Suppress("ktlint:standard:no-blank-line-in-list")
constructor(
	@Json(name = "generic.invalidCommandName") val genericInvalidCommandName: String,
	@Json(name = "generic.unknownCommand") val genericUnknownCommand: String,

	@Json(name = "poll.title") val pollTitle: String,
	@Json(name = "poll.option") val pollOption: String,
	@Json(name = "poll.closedMessage") val pollClosedMessage: String,
	@Json(name = "poll.action.close") val pollActionClose: String,

	@Json(name = "command.coin.heads") val commandCoinHeads: String,
	@Json(name = "command.coin.tails") val commandCoinTails: String,

	@Json(name = "command.poll.missingDescription") val commandPollMissingDescription: String,
	@Json(name = "command.poll.lessThan2Options") val commandPollLessThan2Options: String,

	@Json(name = "command.querypoll.insufficientPermissions") val commandQuerypollInsufficientPermissions: String,
	@Json(name = "command.querypoll.missingId") val commandQuerypollMissingId: String,
	@Json(name = "command.querypoll.noSuchPollWithId") val commandQuerypollNoSuchPollWithId: String,

	@Json(name = "command.8ball.missingQuestion") val command8ballMissingQuestion: String,
	@Json(name = "command.8ball.responses") val command8ballResponses: List<String>,

	@Json(name = "command.emotestats.loading") val commandEmotestatsLoading: String,
	@Json(name = "command.emotestats.errorOccurred") val commandEmotestatsErrorOccurred: String,
	@Json(name = "command.emotestats.empty") val commandEmotestatsEmpty: String,
	@Json(name = "command.emotestats.heading") val commandEmotestatsHeading: String,
	@Json(name = "command.emotestats.statLine") val commandEmotestatsStatLine: String,

	@Json(name = "command.getconfig.insufficientPermissions") val commandGetconfigInsufficientPermissions: String,

	@Json(name = "command.rng.missingMinAndMaxArguments") val commandRngMissingMinAndMaxArguments: String,
	@Json(name = "command.rng.missingMaxArgument") val commandRngMissingMaxArgument: String,
	@Json(name = "command.rng.excessArguments") val commandRngExcessArguments: String,
	@Json(name = "command.rng.minStringTooLong") val commandRngMinStringTooLong: String,
	@Json(name = "command.rng.minInvalidNumber") val commandRngMinInvalidNumber: String,
	@Json(name = "command.rng.minTooSmall") val commandRngMinTooSmall: String,
	@Json(name = "command.rng.minTooBig") val commandRngMinTooBig: String,
	@Json(name = "command.rng.minIsDecimal") val commandRngMinIsDecimal: String,
	@Json(name = "command.rng.maxStringTooLong") val commandRngMaxStringTooLong: String,
	@Json(name = "command.rng.maxInvalidNumber") val commandRngMaxInvalidNumber: String,
	@Json(name = "command.rng.maxTooSmall") val commandRngMaxTooSmall: String,
	@Json(name = "command.rng.maxTooBig") val commandRngMaxTooBig: String,
	@Json(name = "command.rng.maxIsDecimal") val commandRngMaxIsDecimal: String,
	@Json(name = "command.rng.minAndMaxAreEqual") val commandRngMinAndMaxAreEqual: String,
	@Json(name = "command.rng.minGreaterThanMax") val commandRngMinGreaterThanMax: String,
	@Json(name = "command.rng.response") val commandRngResponse: String,

	@Json(name = "command.shutdown.response") val commandShutdownResponse: String,
	@Json(name = "command.shutdown.insufficientPermissions") val commandShutdownInsufficientPermissions: String,
)

@JsonClass(generateAdapter = true)
internal data class CommandDetailsJson(
	@Json(name = "action") val action: ActionJson,
)

@JsonClass(generateAdapter = true)
internal data class ConditionalMessageJson(
	@Json(name = "regex") val regexPattern: String,
	@Json(name = "message") val messageContent: String,
)

@JsonClass(generateAdapter = true)
internal data class ActionJson(
	@Json(name = "response") val responseMessage: String,
)

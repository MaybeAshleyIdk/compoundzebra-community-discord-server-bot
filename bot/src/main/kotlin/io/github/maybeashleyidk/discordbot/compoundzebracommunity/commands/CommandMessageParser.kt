package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.indexOfFirst
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal data class CommandLine(
	val commandName: CommandName,
	val arguments: List<String>,
) {

	override fun toString(): String {
		return buildString {
			this@buildString.append(this@CommandLine.commandName.string)

			this@CommandLine.arguments.forEach { argument: String ->
				this@buildString.append(' ')

				val quoted: Boolean = argument.isEmpty() || argument.any(Char::isWhitespace)

				if (quoted) this@buildString.append('"')
				this@buildString.append(argument)
				if (quoted) this@buildString.append('"')
			}
		}
	}
}

internal sealed class CommandMessageParseResult {
	// TODO: change to data object once we're using Kotlin 1.9.0
	object NotACommandMessage : CommandMessageParseResult()
	data class InvalidCommandName(val invalidCommandNameStr: String) : CommandMessageParseResult()
	data class Success(val commandLine: CommandLine) : CommandMessageParseResult()
}

internal class CommandMessageParser @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configLoader: ConfigLoader,
) {

	@CheckReturnValue
	fun parseMessageContent(content: String): CommandMessageParseResult {
		val config: Config = this.configLoader.load()

		val preparedContent: String = content.trimStart()

		if (!(preparedContent.startsWith(config.commandPrefix.string))) {
			return CommandMessageParseResult.NotACommandMessage
		}

		val commandLineStr: String = preparedContent
			.removePrefix(config.commandPrefix.string)
			.trim()

		return parseCommandLineString(commandLineStr)
	}
}

private sealed class CommandNameParseResult {
	data class InvalidName(val invalidNameStr: String) : CommandNameParseResult()
	data class Success(val commandName: CommandName) : CommandNameParseResult()
}

@CheckReturnValue
private fun parseCommandLineString(string: String): CommandMessageParseResult {
	// region command name

	val commandName: CommandName =
		when (val commandNameParseResult: CommandNameParseResult = parseBeginningCommandName(string)) {
			is CommandNameParseResult.InvalidName -> {
				return CommandMessageParseResult.InvalidCommandName(commandNameParseResult.invalidNameStr)
			}

			is CommandNameParseResult.Success -> {
				commandNameParseResult.commandName
			}
		}

	var i: Int = commandName.string.length

	// skipping (possible) whitespace between command name and arguments
	while ((i < string.length) && string[i].isWhitespace()) {
		++i
	}

	// small optimization. most commands don't have arguments
	if (i >= string.length) {
		val commandLine = CommandLine(commandName, arguments = emptyList())
		return CommandMessageParseResult.Success(commandLine)
	}

	// endregion

	val arguments: MutableList<String> = ArrayList(3)

	while (true) {
		val (argument: String, newIndex: Int) = parseNextArgument(string, startIndex = i)
			?: break

		arguments.add(argument)
		i = newIndex
	}

	val commandLine = CommandLine(commandName, arguments)
	return CommandMessageParseResult.Success(commandLine)
}

@CheckReturnValue
private fun parseBeginningCommandName(string: String): CommandNameParseResult {
	val whitespaceCharIndex: Int = string.indexOfFirst(Char::isWhitespace)

	val commandNameEndIndex: Int =
		if (whitespaceCharIndex != -1) {
			whitespaceCharIndex
		} else {
			string.length
		}

	val commandNameStr: String = string.take(commandNameEndIndex)
	val commandName: CommandName? = CommandName.ofStringOrNull(commandNameStr)

	return if (commandName != null) {
		CommandNameParseResult.Success(commandName)
	} else {
		CommandNameParseResult.InvalidName(commandNameStr)
	}
}

/**
 * Returns the parsed argument and the new index.
 */
@CheckReturnValue
private fun parseNextArgument(string: String, startIndex: Int): Pair<String, Int>? {
	var i: Int = startIndex

	while ((i < string.length) && string[i].isWhitespace()) {
		++i
	}

	if (i >= string.length) {
		return null
	}

	return if (string[i] != '"') {
		val nextWhitespaceIndex: Int = string.indexOfFirst(startIndex = i, Char::isWhitespace)

		val argumentEndIndex: Int =
			if (nextWhitespaceIndex != -1) {
				nextWhitespaceIndex
			} else {
				string.length
			}

		string.substring(i, argumentEndIndex) to argumentEndIndex
	} else {
		// no support for escape characters
		val closingQuotationMarkIndex: Int = string.indexOf('"', startIndex = i + 1)

		val argumentEndIndex: Int =
			if (closingQuotationMarkIndex != -1) {
				closingQuotationMarkIndex
			} else {
				string.length
			}

		string.substring(i + 1, argumentEndIndex) to (argumentEndIndex + 1)
	}
}

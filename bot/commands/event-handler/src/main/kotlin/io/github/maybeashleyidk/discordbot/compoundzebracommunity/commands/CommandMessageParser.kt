package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import javax.inject.Inject

internal data class CommandLine(
	val commandName: CommandName,
	val arguments: List<String>,
) {

	override fun toString(): String {
		return buildString {
			this@buildString.append(this@CommandLine.commandName.toString())

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
	data object NotACommandMessage : CommandMessageParseResult()

	data class InvalidCommandName(val invalidCommandNameStr: String) : CommandMessageParseResult()

	data class Success(val commandLine: CommandLine) : CommandMessageParseResult()
}

internal class CommandMessageParser @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configSupplier: ConfigSupplier,
) {

	fun parseMessageContent(content: String): CommandMessageParseResult {
		val config: Config = this.configSupplier.get()

		val preparedContent: String = content.trimStart()

		if (!(preparedContent.startsWith(config.commandPrefix.toString()))) {
			return CommandMessageParseResult.NotACommandMessage
		}

		val commandLineStr: String = preparedContent
			.removePrefix(config.commandPrefix.toString())
			.trim()

		return parseCommandLineString(commandLineStr)
	}
}

private sealed class CommandNameParseResult {
	data class InvalidName(val invalidNameStr: String) : CommandNameParseResult()

	data class Success(val commandName: CommandName) : CommandNameParseResult()
}

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

	var i: Int = commandName.toString().length

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

private fun parseBeginningCommandName(string: String): CommandNameParseResult {
	val whitespaceCharIndex: Int = string.indexOfFirst(Char::isWhitespace)

	val commandNameEndIndex: Int =
		if (whitespaceCharIndex != -1) {
			whitespaceCharIndex
		} else {
			string.length
		}

	val commandNameStr: String = string.take(commandNameEndIndex)

	if (!(CommandName.isValid(commandNameStr))) {
		return CommandNameParseResult.InvalidName(commandNameStr)
	}

	val commandName: CommandName = CommandName.ofString(commandNameStr)
	return CommandNameParseResult.Success(commandName)
}

/**
 * Returns the parsed argument and the new index.
 */
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

private inline fun String.indexOfFirst(startIndex: Int, predicate: (Char) -> Boolean): Int {
	var i: Int = startIndex
	while (i < this.length) {
		if (predicate(this[i])) {
			return i
		}

		++i
	}

	return -1
}

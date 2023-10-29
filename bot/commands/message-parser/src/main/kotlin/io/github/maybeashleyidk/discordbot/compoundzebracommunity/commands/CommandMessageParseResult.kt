package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

public sealed class CommandMessageParseResult {

	public data object NotACommandMessage : CommandMessageParseResult()

	public data class InvalidCommandName(val invalidCommandNameStr: String) : CommandMessageParseResult()

	public data class Success(val commandLine: CommandLine) : CommandMessageParseResult()
}

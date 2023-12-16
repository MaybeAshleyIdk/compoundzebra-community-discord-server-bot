package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName

public data class CommandLine(
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

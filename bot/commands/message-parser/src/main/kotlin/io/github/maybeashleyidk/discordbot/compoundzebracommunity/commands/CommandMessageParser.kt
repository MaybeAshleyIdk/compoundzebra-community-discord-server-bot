package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import javax.inject.Inject

public class CommandMessageParser @Inject internal constructor(
	private val configSupplier: ConfigSupplier,
) {

	public fun parseMessageContent(content: String): CommandMessageParseResult {
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

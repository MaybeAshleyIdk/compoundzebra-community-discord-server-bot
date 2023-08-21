package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal class CommandEventListener @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val commandMessageParser: CommandMessageParser,
	private val configLoader: ConfigLoader,
	private val commands: Set<@JvmSuppressWildcards Command>,
	private val logger: Logger,
) : EventListener {

	override fun onEvent(event: GenericEvent) {
		if (event !is MessageReceivedEvent) {
			return
		}

		val message: Message = event.message

		if (shouldBotIgnoreMessage(message)) {
			return
		}

		val textChannel: TextChannel = message.channel.asTextChannel()

		val commandLine: CommandLine = this.tryParseMessageContentToCommandLine(message.contentStripped, textChannel)
			?: return

		this.tryFindAndExecuteCommand(commandLine, catalystMessage = message, textChannel)
	}

	@CheckReturnValue
	private fun tryParseMessageContentToCommandLine(messageContent: String, textChannel: TextChannel): CommandLine? {
		val commandMessageParseResult: CommandMessageParseResult =
			this.commandMessageParser.parseMessageContent(messageContent)

		return when (commandMessageParseResult) {
			is CommandMessageParseResult.NotACommandMessage -> {
				null
			}

			is CommandMessageParseResult.InvalidCommandName -> {
				val config: Config = this.configLoader.load()

				val msg: String =
					config.strings.generic.invalidCommandName(commandMessageParseResult.invalidCommandNameStr)

				textChannel.sendMessage(msg).complete()

				null
			}

			is CommandMessageParseResult.Success -> {
				commandMessageParseResult.commandLine
			}
		}
	}

	private fun tryFindAndExecuteCommand(commandLine: CommandLine, catalystMessage: Message, textChannel: TextChannel) {
		val foundCommand: Command? = this.commands
			.firstOrNull { command: Command ->
				command.name == commandLine.commandName
			}

		if (foundCommand == null) {
			val config: Config = this.configLoader.load()

			textChannel.sendMessage(config.strings.generic.unknownCommand(commandLine.commandName.string))
				.complete()

			return
		}

		this.executeCommand(foundCommand, catalystMessage, textChannel)
	}

	private fun executeCommand(command: Command, catalystMessage: Message, textChannel: TextChannel) {
		val logMsg: String = "Executing command ${command.name.toQuotedString()}." +
			" Triggered by \"${catalystMessage.author.name}\" (${catalystMessage.author.idLong})"
		this.logger.logInfo(logMsg)

		try {
			command.execute(catalystMessage, textChannel)
		} catch (e: Exception) {
			this.logger.logError("Command ${command.name.toQuotedString()} threw an exception: $e")
		}
	}
}

@CheckReturnValue
private fun shouldBotIgnoreMessage(message: Message): Boolean {
	return (message.channel.type != ChannelType.TEXT) || message.author.isBot || message.author.isSystem
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult.Handled
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult.NotHandled
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.quoted
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

public class CommandMessageEventHandlerImpl(
	private val commandMessageParser: CommandMessageParser,
	private val configSupplier: ConfigSupplier,
	private val commands: Set<Command>,
	private val logger: Logger,
) : CommandMessageEventHandler {

	public override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		if (event !is MessageReceivedEvent) {
			return NotHandled
		}

		val message: Message = event.message

		if (shouldBotIgnoreMessage(message)) {
			return NotHandled
		}

		val textChannel: TextChannel = message.channel.asTextChannel()

		val commandMessageParseResult: CommandMessageParseResult =
			this.commandMessageParser.parseMessageContent(message.contentStripped)

		return when (commandMessageParseResult) {
			is CommandMessageParseResult.NotACommandMessage -> {
				NotHandled
			}

			is CommandMessageParseResult.InvalidCommandName -> {
				val config: Config = this.configSupplier.get()

				val msg: String =
					config.strings.generic.invalidCommandName(commandMessageParseResult.invalidCommandNameStr)

				textChannel.sendMessage(msg).await()

				Handled
			}

			is CommandMessageParseResult.Success -> {
				this
					.tryFindAndExecuteCommand(
						commandLine = commandMessageParseResult.commandLine,
						catalystMessage = message,
						textChannel,
					)

				Handled
			}
		}
	}

	private suspend fun tryFindAndExecuteCommand(
		commandLine: CommandLine,
		catalystMessage: Message,
		textChannel: TextChannel,
	) {
		val foundCommand: Command? = this.commands
			.firstOrNull { command: Command ->
				command.name.isEquivalentTo(commandLine.commandName)
			}
			?.let { foundCommand: Command ->
				val config: Config = this.configSupplier.get()

				val isCommandEnabled: Boolean = config.disabledCommandNames
					.none { disabledCommandName ->
						disabledCommandName.isEquivalentTo(foundCommand.name)
					}

				foundCommand.takeIf { isCommandEnabled }
			}

		if (foundCommand == null) {
			val config: Config = this.configSupplier.get()

			textChannel.sendMessage(config.strings.generic.unknownCommand(commandLine.commandName))
				.await()

			return
		}

		this.executeCommand(foundCommand, commandLine.arguments, catalystMessage, textChannel)
	}

	private suspend fun executeCommand(
		command: Command,
		arguments: List<String>,
		catalystMessage: Message,
		textChannel: TextChannel,
	) {
		val logMsg: String = "Executing command ${command.name.toQuotedString()}." +
			" Triggered by user ${catalystMessage.author.name.quoted()} (${catalystMessage.author.id})"
		this.logger.logInfo(logMsg)

		try {
			command.execute(arguments, catalystMessage, textChannel)
		} catch (e: Exception) {
			this.logger.logError(e, "Command ${command.name.toQuotedString()} threw an exception")
		}
	}
}

private fun shouldBotIgnoreMessage(message: Message): Boolean {
	return (message.channel.type != ChannelType.TEXT) || message.author.isBot || message.author.isSystem
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import javax.inject.Inject

public class CommandEventHandler @Inject internal constructor(
	private val commandMessageParser: CommandMessageParser,
	private val configSupplier: ConfigSupplier,
	private val commands: Set<@JvmSuppressWildcards Command>,
	private val logger: Logger,
) {

	/**
	 * Returns a boolean that indicates whether the event was consumed or not.
	 */
	public suspend fun handleEvent(event: GenericEvent): Boolean {
		if (event !is MessageReceivedEvent) {
			return false
		}

		val message: Message = event.message

		if (shouldBotIgnoreMessage(message)) {
			return false
		}

		val textChannel: TextChannel = message.channel.asTextChannel()

		val commandMessageParseResult: CommandMessageParseResult =
			this.commandMessageParser.parseMessageContent(message.contentStripped)

		return when (commandMessageParseResult) {
			is CommandMessageParseResult.NotACommandMessage -> {
				false
			}

			is CommandMessageParseResult.InvalidCommandName -> {
				val config: Config = this.configSupplier.get()

				val msg: String =
					config.strings.generic.invalidCommandName(commandMessageParseResult.invalidCommandNameStr)

				textChannel.sendMessage(msg).await()

				true
			}

			is CommandMessageParseResult.Success -> {
				this@CommandEventHandler
					.tryFindAndExecuteCommand(
						commandLine = commandMessageParseResult.commandLine,
						catalystMessage = message,
						textChannel,
					)

				true
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
			" Triggered by \"${catalystMessage.author.name}\" (${catalystMessage.author.idLong})"
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
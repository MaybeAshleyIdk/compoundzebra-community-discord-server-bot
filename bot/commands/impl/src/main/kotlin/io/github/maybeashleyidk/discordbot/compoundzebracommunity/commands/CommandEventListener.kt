package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import javax.inject.Inject

internal class CommandEventListener @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val commandMessageParser: CommandMessageParser,
	private val configSupplier: ConfigSupplier,
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

		// TODO: change this from runBlocking
		runBlocking {
			val commandLine: CommandLine = this@CommandEventListener
				.tryParseMessageContentToCommandLine(
					message.contentStripped,
					textChannel,
				)
				?: return@runBlocking

			this@CommandEventListener.tryFindAndExecuteCommand(commandLine, catalystMessage = message, textChannel)
		}
	}

	private suspend fun tryParseMessageContentToCommandLine(
		messageContent: String,
		textChannel: TextChannel,
	): CommandLine? {
		val commandMessageParseResult: CommandMessageParseResult =
			this.commandMessageParser.parseMessageContent(messageContent)

		return when (commandMessageParseResult) {
			is CommandMessageParseResult.NotACommandMessage -> {
				null
			}

			is CommandMessageParseResult.InvalidCommandName -> {
				val config: Config = this.configSupplier.get()

				val msg: String =
					config.strings.generic.invalidCommandName(commandMessageParseResult.invalidCommandNameStr)

				textChannel.sendMessage(msg).await()

				null
			}

			is CommandMessageParseResult.Success -> {
				commandMessageParseResult.commandLine
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

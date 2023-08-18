package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.trimAndSqueezeWhitespace
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import javax.inject.Inject

internal class CommandEventListener @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configLoader: ConfigLoader,
	private val commands: Set<@JvmSuppressWildcards Command>,
	private val logger: Logger,
) : EventListener {

	override fun onEvent(event: GenericEvent) {
		if (event !is MessageReceivedEvent) {
			return
		}

		val message: Message = event.message
		val channel: MessageChannelUnion = message.channel

		if ((channel.type != ChannelType.TEXT) || message.author.isBot || message.author.isSystem) {
			return
		}

		val textChannel: TextChannel = channel.asTextChannel()
		val preparedMessage: String = message.contentStripped.trimAndSqueezeWhitespace()

		if (!(preparedMessage.startsWith("!"))) {
			return
		}

		val commandNameStr: String = preparedMessage.removePrefix("!")
		val commandName: CommandName? = CommandName.ofStringOrNull(commandNameStr)
		if (commandName == null) {
			val config: Config = this.configLoader.load()
			textChannel.sendMessage(config.strings.genericInvalidCommandName.format(commandNameStr))
				.complete()

			return
		}

		val foundCommand: Command? = this.commands
			.firstOrNull { command: Command ->
				command.name == commandName
			}

		if (foundCommand == null) {
			val config: Config = this.configLoader.load()
			textChannel.sendMessage(config.strings.genericUnknownCommand.format(commandName.string))
				.complete()

			return
		}

		this.executeCommand(foundCommand, catalystMessage = message, textChannel)
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

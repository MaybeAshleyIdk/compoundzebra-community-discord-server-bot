package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConditionalMessage
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
import net.dv8tion.jda.api.requests.RestAction

public class ConditionalMessageEventHandlerImpl(
	private val configSupplier: ConfigSupplier,
	private val logger: Logger,
) : ConditionalMessageEventHandler {

	public override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		if (event !is MessageReceivedEvent) {
			return NotHandled
		}

		val message: Message = event.message

		if (shouldBotIgnoreMessage(message)) {
			return NotHandled
		}

		val textChannel: TextChannel = message.channel.asTextChannel()

		val config: Config = this.configSupplier.get()
		config.conditionalMessages
			.asSequence()
			.filter { conditionalMessage: ConditionalMessage ->
				conditionalMessage.condition.matches(message)
			}
			.map { conditionalMessage: ConditionalMessage ->
				textChannel.sendMessage(conditionalMessage.messageContent)
					.onSuccess { sentMessage: Message ->
						val sb = StringBuilder()
						sb.append("Conditional message (content: ")
						sb.append(conditionalMessage.messageContent.quoted())
						sb.append(", ID: ")
						sb.append(sentMessage.id)
						sb.append(") triggered by message with ID ")
						sb.append(message.id)
						sb.append(", sent by ")
						sb.append(event.author.name.quoted())
						sb.append(" (")
						sb.append(event.author.id)
						sb.append(')')
						this.logger.logInfo(sb.toString())
					}
			}
			.toList()
			.ifEmpty {
				return@handleEvent NotHandled
			}
			.allOf()
			.await()

		return Handled
	}
}

private fun shouldBotIgnoreMessage(message: Message): Boolean {
	return (message.channel.type != ChannelType.TEXT) || message.author.isBot || message.author.isSystem
}

private fun ConditionalMessage.Condition.matches(message: Message): Boolean {
	return this.regex.containsMatchIn(message.contentStripped)
}

private fun <E> Collection<RestAction<E>>.allOf(): RestAction<List<E>> {
	return RestAction.allOf(this)
}

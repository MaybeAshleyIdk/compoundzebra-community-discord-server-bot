package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConditionalMessage
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.requests.RestAction
import javax.inject.Inject

public class ConditionalMessageEventHandler @Inject constructor(
	private val configSupplier: ConfigSupplier,
) {

	public suspend fun handleEvent(event: GenericEvent) {
		if (event !is MessageReceivedEvent) {
			return
		}

		val message: Message = event.message

		if (shouldBotIgnoreMessage(message)) {
			return
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
			}
			.toList()
			.ifEmpty { null }
			?.allOf()
			?.await()
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

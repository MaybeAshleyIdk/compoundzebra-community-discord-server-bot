package io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult.Handled
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult.NotHandled
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.quoted
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

public class PrivateMessageEventHandlerImpl(
	private val logger: Logger,
) : PrivateMessageEventHandler {

	override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		if ((event !is MessageReceivedEvent) || (event.channel.type != ChannelType.PRIVATE)) {
			return NotHandled
		}

		val logMsg: String = "Received private message with ID ${event.message.id} from " +
			"user ${event.message.author.name.quoted()} (${event.message.author.id})"
		this.logger.logInfo(logMsg)

		return Handled
	}
}

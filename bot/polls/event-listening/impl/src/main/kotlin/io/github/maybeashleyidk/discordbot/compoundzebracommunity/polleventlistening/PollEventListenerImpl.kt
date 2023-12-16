package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcomponentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmodification.PollModifier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import kotlinx.coroutines.coroutineScope
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import javax.inject.Inject

public class PollEventListenerImpl @Inject constructor(
	private val pollComponentProtocol: PollComponentProtocol,
	private val pollModifier: PollModifier,
	private val configSupplier: ConfigSupplier,
) : PollEventListener {

	override suspend fun handleEvent(event: GenericEvent) {
		coroutineScope {
			when (event) {
				is StringSelectInteractionEvent -> this@PollEventListenerImpl.onStringSelectInteractionEvent(event)
				is ButtonInteractionEvent -> this@PollEventListenerImpl.onButtonInteractionEvent(event)
			}
		}
	}

	private suspend fun onStringSelectInteractionEvent(event: StringSelectInteractionEvent) {
		// acknowledge.
		// if a lot of votes are coming in at once, then voteOption() may block for an extended period of time
		event.deferEdit().await()

		val pollId: PollId = this.pollComponentProtocol.mapOptionsSelectMenuComponentIdToPollId(event.componentId)
			?: return

		val member: Member = event.member
			?: return

		val optionValue: PollOptionValue = event.values.singleOrNull()
			?.ifEmpty { null }
			?.let(PollOptionValue::ofString)
			?: return

		val newDetails: PollDetails = this.pollModifier.voteOption(pollId, member, optionValue)
			?: return

		val config: Config = this.configSupplier.get()
		event.hook.editOriginal(newDetails.createMessageContent(config.strings.poll))
			.await()
	}

	private suspend fun onButtonInteractionEvent(event: ButtonInteractionEvent) {
		// acknowledge.
		// if a lot of votes are coming in at once, then close() may block for an extended period of time
		event.deferEdit().await()

		val pollId: PollId = this.pollComponentProtocol.mapCloseButtonComponentIdToPollId(event.componentId)
			?: return

		val member: Member = event.member
			?: return

		when (val closeResult: PollModifier.CloseResult = this.pollModifier.closePollIfAllowed(pollId, member)) {
			is PollModifier.CloseResult.Denied -> {
				// TODO: modal?
			}

			is PollModifier.CloseResult.Closed -> {
				val pollDetails: PollDetails = closeResult.pollDetails
					?: return

				val config: Config = this.configSupplier.get()
				val messageContent: String = pollDetails.createMessageContent(config.strings.poll)

				event.hook.editOriginal(messageContent)
					.flatMap { _: Message ->
						event.hook.editOriginalComponents() // this removes all components
					}
					.await()
			}
		}
	}
}

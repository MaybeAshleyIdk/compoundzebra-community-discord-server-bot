package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.componentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollModifier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import kotlinx.coroutines.coroutineScope
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent

public class PollEventHandlerImpl(
	private val pollModifier: PollModifier,
	private val configSupplier: ConfigSupplier,
) : PollEventHandler {

	override suspend fun handleEvent(event: GenericEvent) {
		coroutineScope {
			when (event) {
				is StringSelectInteractionEvent -> this@PollEventHandlerImpl.onStringSelectInteractionEvent(event)
				is ButtonInteractionEvent -> this@PollEventHandlerImpl.onButtonInteractionEvent(event)
			}
		}
	}

	private suspend fun onStringSelectInteractionEvent(event: StringSelectInteractionEvent) {
		// acknowledge.
		// if a lot of votes are coming in at once, then voteOption() may block for an extended period of time
		event.deferEdit().await()

		val pollId: PollId = PollComponentProtocol.mapOptionsSelectMenuComponentIdToPollId(event.componentId)
			?: return

		val member: Member = event.member
			?: return

		val optionValue: PollOptionValue = event.values.singleOrNull()
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

		val pollId: PollId = PollComponentProtocol.mapCloseButtonComponentIdToPollId(event.componentId)
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

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.hooks.EventListener
import javax.inject.Inject

public class PollEventsListener @Inject constructor(
	private val pollManager: PollManager,
	private val configSupplier: ConfigSupplier,
) : EventListener {

	override fun onEvent(event: GenericEvent) {
		// TODO: change this from runBlocking
		runBlocking {
			when (event) {
				is StringSelectInteractionEvent -> this@PollEventsListener.onStringSelectInteractionEvent(event)
				is ButtonInteractionEvent -> this@PollEventsListener.onButtonInteractionEvent(event)
			}
		}
	}

	private suspend fun onStringSelectInteractionEvent(event: StringSelectInteractionEvent) {
		val member: Member = event.member
			?: return

		val optionValue: String = event.values.singleOrNull()
			?: return

		// acknowledge.
		// if a lot of votes are coming in at once, then voteOption() may block for an extended period of time
		event.deferEdit().await()

		val newDetails: PollDetails = this.pollManager.voteOption(member, event.componentId, optionValue)
			?: return

		val config: Config = this.configSupplier.get()
		event.hook.editOriginal(newDetails.createMessageContent(config.strings.poll))
			.await()
	}

	private suspend fun onButtonInteractionEvent(event: ButtonInteractionEvent) {
		// acknowledge.
		// if a lot of votes are coming in at once, then close() may block for an extended period of time
		event.deferEdit().await()

		val closeResult: PollManager.CloseResult = this.pollManager.close(event.member ?: return, event.componentId)
		when (closeResult) {
			is PollManager.CloseResult.WrongComponentId -> Unit

			is PollManager.CloseResult.InsufficientPermissions -> {
				// TODO: modal?
			}

			is PollManager.CloseResult.Closed -> {
				if (closeResult.pollDetails != null) {
					val config: Config = this.configSupplier.get()
					event.hook.editOriginal(closeResult.pollDetails.createMessageContent(config.strings.poll))
						.flatMap { _: Message ->
							event.hook.editOriginalComponents() // this removes all components
						}
						.await()
				}
			}
		}
	}
}

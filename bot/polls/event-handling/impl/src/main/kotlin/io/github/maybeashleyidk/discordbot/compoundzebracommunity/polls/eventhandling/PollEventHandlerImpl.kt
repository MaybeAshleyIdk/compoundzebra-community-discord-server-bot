package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.componentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.messagecreation.PollMessageCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollModifier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import kotlinx.coroutines.coroutineScope
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.interactions.modals.Modal
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import javax.inject.Inject

public class PollEventHandlerImpl @Inject constructor(
	private val pollCreator: PollCreator,
	private val pollMessageCreator: PollMessageCreator,
	private val pollComponentProtocol: PollComponentProtocol,
	private val pollModifier: PollModifier,
	private val configSupplier: ConfigSupplier,
) : PollEventHandler {

	override suspend fun handleEvent(event: GenericEvent) {
		coroutineScope {
			when (event) {
				is SlashCommandInteractionEvent -> this@PollEventHandlerImpl.onSlashCommandInteractionEvent(event)
				is ModalInteractionEvent -> this@PollEventHandlerImpl.onModalInteractionEvent(event)
				is StringSelectInteractionEvent -> this@PollEventHandlerImpl.onStringSelectInteractionEvent(event)
				is ButtonInteractionEvent -> this@PollEventHandlerImpl.onButtonInteractionEvent(event)
			}
		}
	}

	private suspend fun onSlashCommandInteractionEvent(event: SlashCommandInteractionEvent) {
		if (event.name != "poll") {
			return
		}

		val desc = TextInput.create("pollDescription", "Description", TextInputStyle.SHORT)
			.setRequired(true)
			.setMinLength(1)
			.build()

		val options = TextInput.create("pollOptions", "Options", TextInputStyle.PARAGRAPH)
			.setRequired(true)
			.setMinLength(1)
			.setPlaceholder("Place each separate option on a separate line")
			.build()

		val modal = Modal.create("pollModal", "New Poll")
			.addActionRow(desc)
			.addActionRow(options)
			.build()

		event.replyModal(modal).await()
	}

	private suspend fun onModalInteractionEvent(event: ModalInteractionEvent) {
		// TODO: if only spaces are put in the text fields then discord says that there was an error when submitting
		//       do we get an event and do we fuck up or is that on discord?

		if (event.modalId != "pollModal") {
			return
		}

		val member: Member =
			checkNotNull(event.member) {
				TODO()
			}

		val pollDescription: PollDescription? = event.interaction.values
			.single { it.id == "pollDescription" }.asString
			.ifBlank { null }
			?.let(PollDescription::ofString)

		if (pollDescription == null) {
			event
				.reply("Description is empty")
				.setEphemeral(true)
				.await()

			return
		}

		val pollOptionLabels: List<PollOptionLabel> = event.interaction.values
			.single { it.id == "pollOptions" }.asString
			.lines()
			.filter(String::isNotBlank)
			.map(PollOptionLabel::ofString)

		when (pollOptionLabels.size) {
			0 -> {
				event
					.reply("No options")
					.setEphemeral(true)
					.await()

				return
			}

			1 -> {
				event
					.reply("only one option")
					.setEphemeral(true)
					.await()

				return
			}
		}

		val newPollDetails: NewPollDetails = this.pollCreator
			.openNewPoll(
				author = member,
				description = pollDescription,
				optionLabels = pollOptionLabels,
			)

		try {
			val messageCreateData: MessageCreateData = this.pollMessageCreator
				.createMessageData(
					pollId = newPollDetails.id,
					pollDetails = newPollDetails.toEmptyPollDetails(),
				)

			event.reply(messageCreateData).await()
		} catch (e: Throwable) {
			try {
				this.pollCreator.closePoll(pollId = newPollDetails.id, closerMember = member)
			} catch (closeException: Throwable) {
				e.addSuppressed(closeException)
			}

			throw e
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
		val pollId: PollId = this.pollComponentProtocol.mapCloseButtonComponentIdToPollId(event.componentId)
			?: return

		// acknowledge.
		// if a lot of votes are coming in at once, then close() may block for an extended period of time
		event.deferEdit().await()

		val member: Member =
			checkNotNull(event.member) {
				"No member associated with poll close button interaction event"
			}

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

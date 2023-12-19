package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.sending

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.componentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import javax.inject.Inject

public class PollSenderImpl @Inject constructor(
	private val pollCreator: PollCreator,
	private val pollComponentProtocol: PollComponentProtocol,
	private val configSupplier: ConfigSupplier,
) : PollSender {

	override suspend fun createAndSendPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
		targetChannel: GuildMessageChannel,
	) {
		val newPollDetails: NewPollDetails = this.pollCreator.openNewPoll(author, description, optionLabels)

		try {
			val config: Config = this.configSupplier.get()

			val messageContent: String = newPollDetails.createMessageContent(config.strings.poll)

			val selectOptions: List<SelectOption> = newPollDetails.options
				.map { option: PollOption ->
					SelectOption.of(option.label.toString(), option.value.toString())
				}

			val optionsMenu: StringSelectMenu = StringSelectMenu
				.create(this.pollComponentProtocol.mapPollIdToOptionsSelectMenuComponentId(newPollDetails.id))
				.addOptions(selectOptions)
				.build()

			val closeButton: Button = Button
				.primary(
					this.pollComponentProtocol.mapPollIdToCloseButtonComponentId(newPollDetails.id),
					config.strings.poll.action.close,
				)

			targetChannel.sendMessage(messageContent)
				.addActionRow(optionsMenu)
				.addActionRow(closeButton)
				.await()
		} catch (e: Throwable) {
			try {
				this.pollCreator.closePoll(newPollDetails.id, closerMember = author)
			} catch (closeException: Throwable) {
				e.addSuppressed(closeException)
			}

			throw e
		}
	}
}

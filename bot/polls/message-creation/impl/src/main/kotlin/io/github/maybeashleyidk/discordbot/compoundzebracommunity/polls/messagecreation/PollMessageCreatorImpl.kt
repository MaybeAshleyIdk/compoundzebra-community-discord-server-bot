package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.messagecreation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.componentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import javax.inject.Inject

public class PollMessageCreatorImpl @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val pollComponentProtocol: PollComponentProtocol,
) : PollMessageCreator {

	override fun createMessageData(pollId: PollId, pollDetails: PollDetails): MessageCreateData {
		val config: Config = this.configSupplier.get()

		val messageContent: String = pollDetails.createMessageContent(config.strings.poll)

		val selectOptions: List<SelectOption> = pollDetails.options
			.map { option: PollOption ->
				SelectOption.of(option.label.toString(), option.value.toString())
			}

		val optionsMenu: StringSelectMenu = StringSelectMenu
			.create(this.pollComponentProtocol.mapPollIdToOptionsSelectMenuComponentId(pollId))
			.addOptions(selectOptions)
			.build()

		val closeButton: Button = Button
			.primary(
				this.pollComponentProtocol.mapPollIdToCloseButtonComponentId(pollId),
				config.strings.poll.action.close,
			)

		return MessageCreateBuilder()
			.setContent(messageContent)
			.addActionRow(optionsMenu)
			.addActionRow(closeButton)
			.build()
	}
}

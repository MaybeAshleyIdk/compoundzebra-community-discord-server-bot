package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.trimAndSqueezeWhitespace
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import javax.inject.Inject

internal class PollCreationCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val pollCreator: PollCreator,
) : Command(name = CommandName.ofString("poll")) {

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val description: String = arguments.firstOrNull()
			.orEmpty()
			.trimAndSqueezeWhitespace()

		if (description.isEmpty()) {
			val config: Config = this.configSupplier.get()
			textChannel.sendMessage(config.strings.command.poll.missingDescription)
				.complete()

			return
		}

		val pollBuilder: PollCreator.PollBuilder = this.pollCreator
			.create(
				authorId = catalystMessage.author.idLong,
				description = description,
			)

		val selectOptions: List<SelectOption> = arguments.drop(1)
			.mapNotNull { option: String ->
				option
					.trimAndSqueezeWhitespace()
					.ifEmpty { null }
			}
			.map { option: String ->
				val value: String = pollBuilder.addOption(label = option)
				SelectOption.of(option, value)
			}

		if (selectOptions.size < 2) {
			val config: Config = this.configSupplier.get()
			textChannel.sendMessage(config.strings.command.poll.lessThan2Options)
				.complete()

			return
		}

		val optionsMenu: StringSelectMenu = StringSelectMenu.create(pollBuilder.optionsSelectMenuCustomId)
			.addOptions(selectOptions)
			.build()

		val config: Config = this.configSupplier.get()

		val messageContent: String =
			PollDetails(
				authorId = catalystMessage.author.idLong,
				description,
				options = selectOptions
					.map { selectOption: SelectOption ->
						PollOption(
							value = selectOption.value,
							label = selectOption.label,
						)
					},
				voters = emptyMap(),
				closedByUserId = null,
			).createMessageContent(config.strings.poll)

		pollBuilder.open()

		// TODO: another button [Show My Vote] or something like that, that will open a modal?
		textChannel.sendMessage(messageContent)
			.addActionRow(optionsMenu)
			.addActionRow(Button.primary(pollBuilder.closeButtonCustomId, config.strings.poll.action.close))
			.complete()
	}
}

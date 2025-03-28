package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.componentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.trimAndSqueezeWhitespace
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu

public class PollCreationCommand(
	private val configSupplier: ConfigSupplier,
	private val pollCreator: PollCreator,
) : Command(name = CommandName("poll")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val description: PollDescription? = arguments.firstOrNull()
			.orEmpty()
			.trimAndSqueezeWhitespace()
			.let(PollDescription::ofString)
		if (description == null) {
			val config: Config = this.configSupplier.get()
			textChannel.sendMessage(config.strings.command.poll.missingDescription).await()

			return
		}

		val optionLabels: List<PollOptionLabel> = arguments.drop(1)
			.mapNotNull { optionLabel: String ->
				optionLabel
					.trimAndSqueezeWhitespace()
					.let(PollOptionLabel::ofString)
			}
		if (optionLabels.size < 2) {
			val config: Config = this.configSupplier.get()
			textChannel.sendMessage(config.strings.command.poll.lessThan2Options).await()

			return
		}

		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()

		val newPollDetails: NewPollDetails = this.pollCreator
			.openNewPoll(
				author = authorAsGuildMember,
				description = description,
				optionLabels = optionLabels,
			)

		try {
			val config: Config = this.configSupplier.get()

			val messageContent: String = newPollDetails.createMessageContent(config.strings.poll)

			val selectOptions: List<SelectOption> = newPollDetails.options
				.map { option: PollOption ->
					SelectOption.of(option.label.toString(), option.value.toString())
				}

			val optionsMenu: StringSelectMenu = StringSelectMenu
				.create(PollComponentProtocol.mapPollIdToOptionsSelectMenuComponentId(newPollDetails.id))
				.addOptions(selectOptions)
				.build()

			val closeButton: Button = Button
				.primary(
					PollComponentProtocol.mapPollIdToCloseButtonComponentId(newPollDetails.id),
					config.strings.poll.action.close,
				)

			textChannel.sendMessage(messageContent)
				.addActionRow(optionsMenu)
				.addActionRow(closeButton)
				.await()
		} catch (e: Throwable) {
			this.pollCreator.closePoll(newPollDetails.id, closerMember = authorAsGuildMember)
			throw e
		}
	}
}

private suspend fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.await()
}

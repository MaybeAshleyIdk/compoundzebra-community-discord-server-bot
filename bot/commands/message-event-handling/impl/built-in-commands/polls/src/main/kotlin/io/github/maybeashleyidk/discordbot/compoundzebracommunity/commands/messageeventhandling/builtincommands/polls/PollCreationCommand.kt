package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcomponentprotocol.PollComponentProtocol
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.trimAndSqueezeWhitespace
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import javax.inject.Inject

public class PollCreationCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val pollCreator: PollCreator,
	private val pollComponentProtocol: PollComponentProtocol,
) : Command(name = CommandName.ofString("poll")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val description: PollDescription? = arguments.firstOrNull()
			.orEmpty()
			.trimAndSqueezeWhitespace()
			.ifBlank { null }
			?.let(PollDescription::ofString)
		if (description == null) {
			val config: Config = this.configSupplier.get()
			textChannel.sendMessage(config.strings.command.poll.missingDescription).await()

			return
		}

		val optionLabels: List<PollOptionLabel> = arguments.drop(1)
			.mapNotNull { optionLabel: String ->
				optionLabel
					.trimAndSqueezeWhitespace()
					.ifBlank { null }
					?.let(PollOptionLabel::ofString)
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
				.create(this.pollComponentProtocol.mapPollIdToOptionsSelectMenuComponentId(newPollDetails.id))
				.addOptions(selectOptions)
				.build()

			val closeButton: Button = Button
				.primary(
					this.pollComponentProtocol.mapPollIdToCloseButtonComponentId(newPollDetails.id),
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

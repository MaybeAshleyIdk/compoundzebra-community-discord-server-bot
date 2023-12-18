package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.sending.PollSender
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.trimAndSqueezeWhitespace
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class PollCreationCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val pollSender: PollSender,
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

		this.pollSender
			.createAndSendPoll(
				author = authorAsGuildMember,
				description = description,
				optionLabels = optionLabels,
				targetChannel = textChannel,
			)
	}
}

private suspend fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.await()
}

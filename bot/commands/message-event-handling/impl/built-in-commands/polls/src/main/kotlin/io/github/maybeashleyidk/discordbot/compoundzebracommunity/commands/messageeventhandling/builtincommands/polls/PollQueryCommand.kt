package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

public class PollQueryCommand(
	private val configSupplier: ConfigSupplier,
	private val pollHolder: PollHolder,
) : Command(name = CommandName("querypoll")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()
		val config: Config = this.configSupplier.get()

		if (!(authorAsGuildMember.isAllowedToQueryPoll(config.botAdminUserIds))) {
			textChannel.sendMessage(config.strings.command.queryPoll.insufficientPermissions).await()
			return
		}

		val pollIdArg: String = arguments.firstOrNull()
			.orEmpty()
			.trim() // no need to squeeze whitespace here, if *any* whitespace is in-between it's invalid

		if (pollIdArg.isEmpty()) {
			textChannel.sendMessage(config.strings.command.queryPoll.missingId).await()
			return
		}

		val pollDetails: PollDetails? = pollIdArg.toULongOrNull()
			?.let { pollIdULong: ULong ->
				val pollId: PollId = PollId.ofULong(pollIdULong)
				this.pollHolder.getPollDetailsByIdOrNull(pollId)
			}

		if (pollDetails == null) {
			textChannel.sendMessage(config.strings.command.queryPoll.noSuchPollWithId).await()
			return
		}

		catalystMessage.reply(pollDetails.createMessageContent(config.strings.poll)).await()
	}
}

private suspend fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong).await()
}

private fun Member.isAllowedToQueryPoll(botAdminUserIds: Set<String>): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR) || (this.id in botAdminUserIds))
}

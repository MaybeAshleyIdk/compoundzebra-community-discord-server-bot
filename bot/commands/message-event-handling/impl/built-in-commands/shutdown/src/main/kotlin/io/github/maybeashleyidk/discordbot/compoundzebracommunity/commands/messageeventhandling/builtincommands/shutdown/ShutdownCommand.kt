package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.shutdown

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class ShutdownCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val shutdownRequester: ShutdownRequester,
) : Command(name = CommandName.Companion.ofString("shutdown")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()
		val config: Config = this.configSupplier.get()

		if (!(authorAsGuildMember.isAllowedToShutdownBot(config.botAdminUserIds))) {
			textChannel.sendMessage(config.strings.command.shutdown.insufficientPermissions).await()
			return
		}

		textChannel.sendMessage(config.strings.command.shutdown.response).await()

		// we can't actually do the shutdown procedure here, since we're still in the event listener.
		// if we were to call JDA.shutdown(), then it wouldn't do anything and JDA.awaitShutdown() would never return.
		// it needs to be deferred until we're out of the event listener
		this.shutdownRequester.requestShutdown()
	}
}

private suspend fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong).await()
}

private fun Member.isAllowedToShutdownBot(botAdminUserIds: Set<String>): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR) || (this.id in botAdminUserIds))
}

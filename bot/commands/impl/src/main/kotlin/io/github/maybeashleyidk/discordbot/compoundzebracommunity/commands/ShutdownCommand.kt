package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.ShutdownRequester
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal class ShutdownCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val shutdownRequester: ShutdownRequester,
) : Command(name = CommandName.ofString("shutdown")) {

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()
		val config: Config = this.configSupplier.get()

		if (!(authorAsGuildMember.isAllowedToShutdownBot(config.botAdminUserIds))) {
			textChannel.sendMessage(config.strings.command.shutdown.insufficientPermissions)
				.complete()

			return
		}

		textChannel.sendMessage(config.strings.command.shutdown.response)
			.complete()

		// we can't actually do the shutdown procedure here, since we're still in the event listener.
		// if we were to call JDA.shutdown(), then it wouldn't do anything and JDA.awaitShutdown() would never return.
		// it needs to be deferred until we're out of the event listener
		this.shutdownRequester.requestShutdown()
	}
}

@CheckReturnValue
private fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.complete()
}

@CheckReturnValue
private fun Member.isAllowedToShutdownBot(botAdminUserIds: Set<String>): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR) || (this.id in botAdminUserIds))
}

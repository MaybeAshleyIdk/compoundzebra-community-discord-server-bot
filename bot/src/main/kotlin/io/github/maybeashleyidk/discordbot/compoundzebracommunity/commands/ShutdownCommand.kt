package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.ConfigLoader
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal class ShutdownCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configLoader: ConfigLoader,
	private val shutdownManager: ShutdownManager,
) : Command(name = CommandName.ofString("shutdown")) {

	override fun execute(catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()
		val config: Config = this.configLoader.load()

		if (!(authorAsGuildMember.isAllowedToShutdownBot())) {
			textChannel.sendMessage(config.strings.commandShutdownInsufficientPermissions)
				.complete()

			return
		}

		textChannel.sendMessage(config.strings.commandShutdownResponse)
			.complete()

		// we can't actually do the shutdown procedure here, since we're still in the event listener.
		// if we were to call JDA.shutdown(), then it wouldn't do anything and JDA.awaitShutdown() would never return.
		// it needs to be deferred until we're out of the event listener
		this.shutdownManager.requestShutdown()
	}
}

@CheckReturnValue
private fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.complete()
}

@CheckReturnValue
private fun Member.isAllowedToShutdownBot(): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR))
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.annotation.CheckReturnValue
import javax.inject.Inject

class ShutdownCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val shutdownManager: ShutdownManager,
	private val logger: Logger,
) : Command(name = CommandName.ofString("shutdown")) {

	override fun execute(catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member? = catalystMessage.getAuthorAsGuildMember()

		if (authorAsGuildMember == null) {
			this.logger.logWarning("Guild.getMemberById() returned null for some reason. Bailing out")

			textChannel.sendMessage("Sorry, a problem occurred trying to shutdown")
				.complete()

			return
		}

		if (!(authorAsGuildMember.isAllowedToShutdownBot())) {
			textChannel.sendMessage("You are not allowed to use this command")
				.complete()

			return
		}

		textChannel.sendMessage("Shutting down. Bye bye...")
			.complete()

		// we can't actually do the shutdown procedure here, since we're still in the event listener.
		// if we were to call JDA.shutdown(), then it wouldn't do anything and JDA.awaitShutdown() would never return.
		// it needs to be deferred until we're out of the event listener
		this.shutdownManager.requestShutdown()
	}
}

@CheckReturnValue
private fun Message.getAuthorAsGuildMember(): Member? {
	return this.guild.getMemberById(this.author.idLong)
}

@CheckReturnValue
private fun Member.isAllowedToShutdownBot(): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR))
}

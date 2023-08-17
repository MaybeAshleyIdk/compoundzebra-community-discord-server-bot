package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.ShutdownManager
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

class ShutdownCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : Command(name = CommandName.ofString("shutdown")) {

	override fun execute(catalystMessage: Message, textChannel: TextChannel) {
		textChannel.sendMessage("Shutting down. Bye bye...")
			.complete()

		// we can't actually do the shutdown procedure here, since we're still in the event listener.
		// if we were to call JDA.shutdown(), then it wouldn't do anything and JDA.awaitShutdown() would never return.
		// it needs to be deferred until we're out of the event listener
		this.shutdownManager.requestShutdown()
	}
}

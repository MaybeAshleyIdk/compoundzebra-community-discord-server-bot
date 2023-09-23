package io.github.maybeashleyidk.discordbot.compoundzebracommunity.scheduledmessages

import net.dv8tion.jda.api.JDA as Jda

public interface ScheduledMessagesManager {

	public fun start(jda: Jda)

	public fun stop()
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandname.CommandName
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

public abstract class Command(public val name: CommandName) {

	public abstract suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel)
}

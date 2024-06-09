package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

public abstract class Command(public val name: CommandName) {

	public abstract suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel)
}

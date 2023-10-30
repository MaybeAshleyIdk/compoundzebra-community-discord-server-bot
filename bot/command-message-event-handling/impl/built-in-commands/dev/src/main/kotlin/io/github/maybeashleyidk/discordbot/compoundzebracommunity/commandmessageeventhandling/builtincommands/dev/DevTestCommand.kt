package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.dev

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandname.CommandName
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class DevTestCommand @Inject constructor(
) : Command(name = CommandName.ofString("devtest")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
	}
}

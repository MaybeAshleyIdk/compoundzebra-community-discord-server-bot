package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler.builtincommands.dev

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class DevTestCommand @Inject constructor(
) : Command(name = CommandName.ofString("devtest")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
	}
}

@file:Suppress("ktlint:standard:parameter-list-spacing", "ktlint:standard:paren-spacing")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.dev

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class DevTestCommand @Inject constructor(
) : Command(name = CommandName.ofString("devtest")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
	}
}

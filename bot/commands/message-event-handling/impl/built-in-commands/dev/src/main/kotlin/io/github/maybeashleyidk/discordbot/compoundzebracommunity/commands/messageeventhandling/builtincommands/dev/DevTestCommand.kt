@file:Suppress("ktlint:standard:parameter-list-spacing", "ktlint:standard:paren-spacing")

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.dev

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.RecentMessagesService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import java.net.URI
import javax.inject.Inject

public class DevTestCommand @Inject constructor(
	private val recentMessagesService: RecentMessagesService,
) : Command(name = CommandName.ofString("devtest")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val list = this.recentMessagesService.getRecentMessagesOf(textChannel)
			.toList()
		val first = list.firstOrNull()
		val last = list.lastOrNull()
		catalystMessage.reply("first: ${first?.link()}\nlast: ${last?.link()}").await()
	}
}

private fun Message.link(): URI {
	return URI.create("https://discord.com/channels/${this.guildId ?: "@me"}/${this.channelId}/${this.id}")
}

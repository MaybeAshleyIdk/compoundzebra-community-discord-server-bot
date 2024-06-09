package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.sourcecode

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class SourceCodeCommand @Inject constructor() : Command(CommandName.ofString("source" + "code")) {

	private companion object {
		const val REPO_URL: String = "https://github.com/MaybeAshleyIdk/compoundzebra-community-discord-server-bot"
	}

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		@Suppress("RemoveRedundantQualifierName")
		catalystMessage.reply(SourceCodeCommand.REPO_URL)
			.await()
	}
}

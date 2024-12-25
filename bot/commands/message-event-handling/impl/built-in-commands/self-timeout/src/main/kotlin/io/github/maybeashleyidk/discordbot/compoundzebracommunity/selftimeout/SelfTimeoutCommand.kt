package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

public class SelfTimeoutCommand(
	private val selfTimeoutService: SelfTimeoutService,
) : Command(name = CommandName.ofString("TimeToDie".lowercase())) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val author: Member = catalystMessage.getAuthorAsGuildMember()
		this.selfTimeoutService.timeOutMember(member = author)
	}
}

private suspend fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.await()
}

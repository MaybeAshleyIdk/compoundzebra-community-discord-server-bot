package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandname.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.inject.Inject

public class SelfTimeoutCommand @Inject constructor(
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

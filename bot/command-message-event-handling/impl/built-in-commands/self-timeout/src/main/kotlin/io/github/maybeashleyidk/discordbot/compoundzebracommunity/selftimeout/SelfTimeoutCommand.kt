package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandname.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import java.time.Duration
import javax.inject.Inject

public class SelfTimeoutCommand @Inject constructor() : Command(name = CommandName.ofString("TimeToDie".lowercase())) {

	private companion object {
		val TIMEOUT_DURATION: Duration = Duration.ofSeconds(5)
	}

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		if (!(textChannel.guild.selfMember.hasPermission(Permission.MODERATE_MEMBERS))) {
			return
		}

		@Suppress("RemoveRedundantQualifierName")
		textChannel.guild.timeoutFor(catalystMessage.author, SelfTimeoutCommand.TIMEOUT_DURATION).await()
	}
}

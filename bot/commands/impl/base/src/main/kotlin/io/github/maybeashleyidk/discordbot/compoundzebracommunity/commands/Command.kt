package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.annotation.CheckReturnValue

public abstract class Command(public val name: CommandName) {

	public abstract fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel)

	@CheckReturnValue
	override fun toString(): String {
		return "Command ${this.name.toQuotedString()}"
	}
}

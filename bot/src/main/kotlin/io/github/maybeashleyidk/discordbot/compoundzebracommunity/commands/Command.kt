package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import javax.annotation.CheckReturnValue

abstract class Command(val name: CommandName) {

	abstract fun execute(catalystMessage: Message, textChannel: TextChannel)

	@CheckReturnValue
	override fun toString(): String {
		return "Command ${this.name.toQuotedString()}"
	}
}

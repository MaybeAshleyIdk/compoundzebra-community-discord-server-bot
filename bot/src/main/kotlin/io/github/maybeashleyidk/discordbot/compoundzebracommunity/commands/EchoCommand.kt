package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import javax.annotation.CheckReturnValue

internal class EchoCommand @Suppress("ktlint:standard:annotation") @AssistedInject constructor(
	@Assisted("name") nameString: String,
	@Assisted("responseMessage") val responseMessage: String,
) : Command(CommandName.ofString(nameString)) {

	@AssistedFactory
	fun interface Factory {

		@CheckReturnValue
		fun build(
			@Assisted("name") nameString: String,
			@Assisted("responseMessage") responseMessage: String,
		): EchoCommand
	}

	override fun execute(catalystMessage: Message, textChannel: TextChannel) {
		val action: MessageCreateAction = textChannel.sendMessage(this.responseMessage)
		action.complete()
	}
}

@CheckReturnValue
internal fun EchoCommand.Factory.build(
	name: CommandName,
	responseMessage: String,
): EchoCommand {
	return this.build(
		nameString = name.string,
		responseMessage = responseMessage,
	)
}

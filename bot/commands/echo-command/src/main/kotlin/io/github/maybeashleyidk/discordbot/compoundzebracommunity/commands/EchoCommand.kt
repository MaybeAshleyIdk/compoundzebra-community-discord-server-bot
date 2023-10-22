package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

public class EchoCommand @Suppress("ktlint:standard:annotation") @AssistedInject constructor(
	@Assisted("name") nameString: String,
	@Assisted("responseMessage") private val responseMessage: String,
) : Command(CommandName.ofString(nameString)) {

	@AssistedFactory
	public fun interface Factory {

		public fun build(@Assisted("name") nameString: String, @Assisted("responseMessage") responseMessage: String): EchoCommand
	}

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		textChannel.sendMessage(this.responseMessage).await()
	}
}

public fun EchoCommand.Factory.build(name: CommandName, responseMessage: String): EchoCommand {
	return this.build(
		nameString = name.string,
		responseMessage = responseMessage,
	)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

public class PredefinedResponseCommand @AssistedInject constructor(
	@Assisted("name") nameString: String,
	@Assisted("responseMessage") private val responseMessage: String,
) : Command(CommandName.ofString(nameString)) {

	@AssistedFactory
	public fun interface Factory {

		public fun build(
			@Assisted("name") nameString: String,
			@Assisted("responseMessage") responseMessage: String,
		): PredefinedResponseCommand
	}

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		textChannel.sendMessage(this.responseMessage).await()
	}
}

public fun PredefinedResponseCommand.Factory.build(
	name: CommandName,
	responseMessage: String,
): PredefinedResponseCommand {
	return this.build(
		nameString = name.toString(),
		responseMessage = responseMessage,
	)
}

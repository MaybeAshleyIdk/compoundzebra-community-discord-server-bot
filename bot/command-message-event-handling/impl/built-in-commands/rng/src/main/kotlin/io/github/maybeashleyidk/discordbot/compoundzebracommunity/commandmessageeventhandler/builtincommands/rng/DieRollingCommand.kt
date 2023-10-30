package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler.builtincommands.rng

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import java.text.NumberFormat
import javax.inject.Inject
import kotlin.random.Random

public class DieRollingCommand @Inject constructor() : Command(name = CommandName.ofString("rtd")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val roll: Int = Random.nextInt(from = 1, until = 7)

		val outputFormat: NumberFormat = NumberFormat.getIntegerInstance(catalystMessage.guild.locale.toLocale())

		catalystMessage.reply(outputFormat.format(roll)).await()
	}
}

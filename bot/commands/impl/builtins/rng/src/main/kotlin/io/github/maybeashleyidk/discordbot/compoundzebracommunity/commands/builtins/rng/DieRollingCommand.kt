package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.rng

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.DiscordLocale
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

public class DieRollingCommand @Inject constructor() : Command(name = CommandName.ofString("rtd")) {

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val roll: Int = Random.nextInt(from = 1, until = 7)

		val outputFormat: NumberFormat = NumberFormat.getIntegerInstance(catalystMessage.guild.locale.toJvmLocale())

		catalystMessage.reply(outputFormat.format(roll))
			.complete()
	}
}

private fun DiscordLocale.toJvmLocale(): Locale {
	return (Locale.forLanguageTag(this.locale) ?: Locale.ROOT)
}

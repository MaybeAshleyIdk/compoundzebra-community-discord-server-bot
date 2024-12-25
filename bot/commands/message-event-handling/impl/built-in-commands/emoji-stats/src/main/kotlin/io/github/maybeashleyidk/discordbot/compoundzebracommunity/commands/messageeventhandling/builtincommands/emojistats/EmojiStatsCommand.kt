package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.emojistats

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.emoji.CustomEmoji
import java.text.NumberFormat

public class EmojiStatsCommand(
	private val configSupplier: ConfigSupplier,
	private val emojiStatsManager: EmojiStatsManager,
) : Command(name = CommandName.ofString("emote" + "stats")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val loadingMessage: Message = catalystMessage
			.reply(this.configSupplier.get().strings.command.emojiStats.loading)
			.await()

		val emojiStats: List<Map.Entry<CustomEmoji, Long>> =
			try {
				this.emojiStatsManager
					.countUsedEmojisOfUserInGuild(
						user = catalystMessage.author,
						guild = catalystMessage.guild,
					)
					.entries
					.sortedByDescending { (_: CustomEmoji, count: Long) ->
						count
					}
			} catch (e: Throwable) {
				val config: Config = this.configSupplier.get()
				loadingMessage
					.editMessage(config.strings.command.emojiStats.errorOccurred)
					.await()

				throw e
			}

		if (emojiStats.isEmpty()) {
			val config: Config = this.configSupplier.get()
			loadingMessage
				.editMessage(config.strings.command.emojiStats.empty)
				.await()

			return
		}

		val config: Config = this.configSupplier.get()

		val sb = StringBuilder()

		val numberFormat: NumberFormat = NumberFormat.getIntegerInstance(catalystMessage.guild.locale.toLocale())

		sb.append(config.strings.command.emojiStats.heading(catalystMessage.author.asMention))
		for ((emoji: CustomEmoji, count: Long) in emojiStats) {
			sb.append('\n')
			sb.append(config.strings.command.emojiStats.statLine(emoji.formatted, numberFormat.format(count)))
		}

		loadingMessage
			.editMessage(sb.toString())
			.await()
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.emojistats

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.EmojiStatsManager
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.emoji.CustomEmoji
import net.dv8tion.jda.api.interactions.DiscordLocale
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

internal class EmojiStatsCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val emojiStatsManager: EmojiStatsManager,
) : Command(name = CommandName.ofString("emote" + "stats")) {

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val loadingMessage: Message = catalystMessage
			.reply(this.configSupplier.get().strings.command.emojiStats.loading)
			.complete()

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
					.complete()

				throw e
			}

		if (emojiStats.isEmpty()) {
			val config: Config = this.configSupplier.get()
			loadingMessage
				.editMessage(config.strings.command.emojiStats.empty)
				.complete()

			return
		}

		val config: Config = this.configSupplier.get()

		val sb = StringBuilder()

		val numberFormat: NumberFormat = NumberFormat.getIntegerInstance(catalystMessage.guild.locale.toJvmLocale())

		sb.append(config.strings.command.emojiStats.heading(catalystMessage.author.asMention))
		for ((emoji: CustomEmoji, count: Long) in emojiStats) {
			sb.append('\n')
			sb.append(config.strings.command.emojiStats.statLine(emoji.formatted, numberFormat.format(count)))
		}

		loadingMessage
			.editMessage(sb.toString())
			.complete()
	}
}

private fun DiscordLocale.toJvmLocale(): Locale {
	return (Locale.forLanguageTag(this.locale) ?: Locale.ROOT)
}

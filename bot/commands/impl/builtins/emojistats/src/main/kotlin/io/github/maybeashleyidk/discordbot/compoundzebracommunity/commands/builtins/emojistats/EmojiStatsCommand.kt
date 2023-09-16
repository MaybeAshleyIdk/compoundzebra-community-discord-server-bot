package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.emojistats

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.EmojiStatsManager
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.emoji.CustomEmoji
import net.dv8tion.jda.api.interactions.DiscordLocale
import java.text.NumberFormat
import java.util.Locale
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal class EmojiStatsCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val emojiStatsManager: EmojiStatsManager,
) : Command(name = CommandName.ofString("emote" + "stats")) {

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val loadingMessage: Message? =
			if (catalystMessage.guildChannel.hasSelfMemberPermission(Permission.MESSAGE_MANAGE)) {
				val config: Config = this.configSupplier.get()
				catalystMessage
					.reply(config.strings.command.emojiStats.loading)
					.complete()
			} else {
				null
			}

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
				if (loadingMessage != null) {
					loadingMessage
						.editMessage(config.strings.command.emojiStats.errorOccurred)
						.complete()
				} else {
					catalystMessage
						.reply(config.strings.command.emojiStats.errorOccurred)
						.complete()
				}

				throw e
			}

		if (emojiStats.isEmpty()) {
			val config: Config = this.configSupplier.get()
			if (loadingMessage != null) {
				loadingMessage
					.editMessage(config.strings.command.emojiStats.empty)
					.complete()
			} else {
				catalystMessage
					.reply(config.strings.command.emojiStats.empty)
					.complete()
			}

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

		if (loadingMessage != null) {
			loadingMessage
				.editMessage(sb.toString())
				.complete()
		} else {
			catalystMessage
				.reply(sb.toString())
				.complete()
		}
	}
}

@CheckReturnValue
private fun GuildChannel.hasSelfMemberPermission(permission: Permission): Boolean {
	return this.guild.selfMember.hasPermission(this, permission)
}

@CheckReturnValue
private fun DiscordLocale.toJvmLocale(): Locale {
	return (Locale.forLanguageTag(this.locale) ?: Locale.ROOT)
}

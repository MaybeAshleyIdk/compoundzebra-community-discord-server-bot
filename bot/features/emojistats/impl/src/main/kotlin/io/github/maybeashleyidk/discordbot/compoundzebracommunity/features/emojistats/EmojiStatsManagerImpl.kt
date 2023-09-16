package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.entities.emoji.CustomEmoji
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.entities.emoji.EmojiUnion
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji
import javax.inject.Inject

private val CUSTOM_EMOJI_REGEX_PATTERN: Regex = Regex("(?<!\\\\)<:[^:]+:(?<id>0|[1-9][0-9]*)>")

public class EmojiStatsManagerImpl @Inject constructor(
	private val logger: Logger,
) : EmojiStatsManager {

	override fun countUsedEmojisOfUserInGuild(user: User, guild: Guild): Map<CustomEmoji, Long> {
		val emojiIdCounter: MutableMap<EmojiSnowflakeId, Long> = hashMapOf()

		for (guildChannel: GuildChannel in guild.channels) {
			this.countUsedEmojisOfUserInGuildChannel(emojiIdCounter, user, guildChannel)
		}

		return this.mapEmojiIdCounterToEmojiCounter(guild, emojiIdCounter)
	}

	private fun countUsedEmojisOfUserInGuildChannel(
		out: MutableMap<EmojiSnowflakeId, Long>,
		user: User,
		guildChannel: GuildChannel,
	) {
		if (!(guildChannel.guild.selfMember.hasPermission(guildChannel, Permission.VIEW_CHANNEL))) {
			return
		}

		when (guildChannel.type) {
			ChannelType.TEXT -> {
				val guildTextChannel: TextChannel = (guildChannel as TextChannel)

				countUsedEmojisOfUserInGuildMessageChannel(out, user, guildTextChannel)
				countUsedEmojisOfUserInThreadContainer(
					out,
					user,
					guildTextChannel,
					includeArchivedPrivateThreads = true,
				)
			}

			ChannelType.NEWS -> {
				val guildNewsChannel: NewsChannel = (guildChannel as NewsChannel)

				countUsedEmojisOfUserInGuildMessageChannel(out, user, guildNewsChannel)
				countUsedEmojisOfUserInThreadContainer(
					out,
					user,
					guildNewsChannel,
					includeArchivedPrivateThreads = true,
				)
			}

			ChannelType.VOICE -> countUsedEmojisOfUserInGuildMessageChannel(out, user, (guildChannel as VoiceChannel))

			ChannelType.STAGE -> countUsedEmojisOfUserInGuildMessageChannel(out, user, (guildChannel as StageChannel))

			ChannelType.FORUM -> {
				countUsedEmojisOfUserInThreadContainer(
					out,
					user,
					(guildChannel as ForumChannel),
					includeArchivedPrivateThreads = false,
				)
			}

			ChannelType.PRIVATE -> Unit

			ChannelType.CATEGORY -> Unit

			ChannelType.GROUP -> {
				val msg: String =
					"${EmojiStatsManagerImpl::class.java.simpleName}: Group channel type." +
						" Channel ID ${guildChannel.id}"
				this.logger.logWarning(msg)
			}

			ChannelType.GUILD_NEWS_THREAD -> {
				val msg: String =
					"${EmojiStatsManagerImpl::class.java.simpleName}: Guild news thread channel type." +
						" Channel ID ${guildChannel.id}"
				this.logger.logWarning(msg)
			}

			ChannelType.GUILD_PUBLIC_THREAD -> {
				val msg: String =
					"${EmojiStatsManagerImpl::class.java.simpleName}: Guild public thread channel type." +
						" Channel ID ${guildChannel.id}"
				this.logger.logWarning(msg)
			}

			ChannelType.GUILD_PRIVATE_THREAD -> {
				val msg: String =
					"${EmojiStatsManagerImpl::class.java.simpleName}: Guild private thread channel type." +
						" Channel ID ${guildChannel.id}"
				this.logger.logWarning(msg)
			}

			ChannelType.UNKNOWN -> {
				val msg: String =
					"${EmojiStatsManagerImpl::class.java.simpleName}: Unknown channel type." +
						" Channel ID ${guildChannel.id}"
				this.logger.logWarning(msg)
			}
		}
	}

	private fun mapEmojiIdCounterToEmojiCounter(
		guild: Guild,
		emojiIdCounter: MutableMap<EmojiSnowflakeId, Long>,
	): Map<CustomEmoji, Long> {
		val guildEmojis: Map<EmojiSnowflakeId, CustomEmoji> = guild.retrieveEmojis().complete()
			.let { guildEmojiList: List<RichCustomEmoji> ->
				guildEmojiList
					.associateByTo(HashMap(guildEmojiList.size)) { emoji: RichCustomEmoji ->
						EmojiSnowflakeId(emoji.id)
					}
			}

		val emojiCounter: MutableMap<CustomEmoji, Long> = mutableMapOf()

		for ((emojiId: EmojiSnowflakeId, count: Long) in emojiIdCounter) {
			val guildEmoji: CustomEmoji = guildEmojis[emojiId]
				?: continue

			emojiCounter[guildEmoji] = count
		}

		return emojiCounter
	}
}

private fun countUsedEmojisOfUserInGuildMessageChannel(
	out: MutableMap<EmojiSnowflakeId, Long>,
	user: User,
	guildMessageChannel: GuildMessageChannel,
) {
	for (message: Message in guildMessageChannel.iterableHistory) {
		countUsedEmojisOfUserInMessage(out, user, message)
	}
}

private fun countUsedEmojisOfUserInThreadContainer(
	out: MutableMap<EmojiSnowflakeId, Long>,
	user: User,
	threadContainer: IThreadContainer,
	includeArchivedPrivateThreads: Boolean,
) {
	val archivedPrivateThreads: Iterable<ThreadChannel> =
		when {
			!includeArchivedPrivateThreads -> {
				emptyList()
			}

			threadContainer.guild.selfMember.hasPermission(threadContainer, Permission.MANAGE_THREADS) -> {
				threadContainer.retrieveArchivedPrivateThreadChannels()
			}

			else -> {
				threadContainer.retrieveArchivedPrivateJoinedThreadChannels()
			}
		}

	sequenceOf<Iterable<ThreadChannel>>(
		threadContainer.threadChannels,
		threadContainer.retrieveArchivedPublicThreadChannels(),
		archivedPrivateThreads,
	).flatten()
		.flatMap(ThreadChannel::getIterableHistory)
		.forEach { threadMessage: Message ->
			countUsedEmojisOfUserInMessage(out, user, threadMessage)
		}
}

private fun countUsedEmojisOfUserInMessage(out: MutableMap<EmojiSnowflakeId, Long>, user: User, message: Message) {
	if (user.idLong == message.author.idLong) {
		countCustomEmojisInMessageContent(out, message.contentRaw)
	}

	for (reaction: MessageReaction in message.reactions) {
		countUsedEmojisOfUserInMessageReaction(out, user, reaction)
	}
}

private fun countCustomEmojisInMessageContent(out: MutableMap<EmojiSnowflakeId, Long>, messageContent: String) {
	// TODO: don't count if in backticks (both inline code and code block)

	val emojiIds: Sequence<EmojiSnowflakeId> = CUSTOM_EMOJI_REGEX_PATTERN.findAll(messageContent)
		.map { match: MatchResult ->
			EmojiSnowflakeId(checkNotNull(match.groups["id"]).value)
		}

	for (emojiId: EmojiSnowflakeId in emojiIds) {
		out.incrementKey(emojiId)
	}
}

private fun countUsedEmojisOfUserInMessageReaction(
	out: MutableMap<EmojiSnowflakeId, Long>,
	user: User,
	messageReaction: MessageReaction,
) {
	val emojiUnion: EmojiUnion = messageReaction.emoji
	val customEmoji: CustomEmoji =
		when (emojiUnion.type) {
			Emoji.Type.UNICODE -> return
			Emoji.Type.CUSTOM -> emojiUnion.asCustom()
		}

	val userReacted: Boolean = messageReaction.retrieveUsers().complete()
		.any { reactedUser: User ->
			reactedUser.idLong == user.idLong
		}

	if (userReacted) {
		out.incrementKey(EmojiSnowflakeId(customEmoji.id))
	}
}

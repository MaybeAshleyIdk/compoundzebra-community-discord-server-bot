package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.interactions.DiscordLocale
import java.time.Instant
import java.util.Locale

public data class Timestamp(
	public val seconds: Long,
	public val style: TimestampStyle,
) : TextElement {

	public companion object {

		public const val MIN: Long = -8_640_000_000_000
		public const val MAX: Long = 8_640_000_000_000
	}

	init {
		require(this.seconds in (Companion.MIN..Companion.MAX))
	}

	public val instant: Instant
		get() {
			return Instant.ofEpochSecond(this.seconds)
		}

	override fun toRawString(): String {
		val sb = StringBuilder()

		sb.append("<t:")
		sb.append(this.seconds)
		if (this.style != TimestampStyle.DEFAULT) {
			sb.append(':').append(this.style.ch)
		}
		sb.append('>')

		return sb.toString()
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		val locale: Locale = when (channelContext) {
			is PrivateChannel -> {
				val user: User = channelContext.retrieveUser().await()

				user.mutualGuilds
					.map(Guild::getLocale)
					.getMostPopular()
			}

			is GuildChannel -> channelContext.guild.locale

			else -> null
		}
			?.takeIf { locale: DiscordLocale ->
				locale != DiscordLocale.UNKNOWN
			}
			?.toLocale() ?: Locale.US


		return this.instant.format(this.style, locale)
	}
}

private fun <T : Any> Iterable<T>.countEach(): Map<T, Int> {
	val map: MutableMap<T, Int> = (if (this is Collection) HashMap(this.size) else HashMap())

	for (element: T in this) {
		map.merge(element, 1, Int::plus)
	}

	return map
}

private fun <T : Any> List<T>.getMostPopular(): T? {
	if (this.isEmpty()) {
		return null
	}

	if (this.size == 1) {
		return this[0]
	}

	val counts: Map<T, Int> = this.countEach()

	val (first: Map.Entry<T, Int>, second: Map.Entry<T, Int>) = counts.entries
		.sortedBy(Map.Entry<*, Int>::value)
		.take(2)

	if (first.value == second.value) {
		return null
	}

	return first.key
}

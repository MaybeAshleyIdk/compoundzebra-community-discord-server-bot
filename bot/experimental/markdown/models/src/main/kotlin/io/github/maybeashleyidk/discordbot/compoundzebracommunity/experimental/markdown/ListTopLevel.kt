package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

public interface TopLevelListItem {

	public val bullet: ListItemBullet
}

public data class TopLevelList(
	public val level: UInt,
	public val items: List<TopLevelListItem>,
) : MarkdownElement, MarkdownThing {

	public companion object {

		public const val MAX_LEVEL: UInt = 10u
	}

	init {
		require(this.level <= Companion.MAX_LEVEL)
		require(this.items.isNotEmpty())
	}

	override fun toRawString(): String {
		TODO("Not yet implemented")
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		TODO("Not yet implemented")
	}

	public fun determineStyle(): MListStyle {
		val firstItem: TopLevelListItem = this.items.first()

		return when (firstItem.bullet) {
			is ListItemBullet.Ordered -> MListStyle.ORDERED
			is ListItemBullet.Unordered -> MListStyle.UNORDERED
		}
	}
}

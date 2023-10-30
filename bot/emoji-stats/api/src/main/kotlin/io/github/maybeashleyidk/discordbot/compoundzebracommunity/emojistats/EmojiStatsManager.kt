package io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.emoji.CustomEmoji

public interface EmojiStatsManager {

	/**
	 * Returns a mapping of custom guild emoji to the count of how many times the [user] has sent that emoji.
	 */
	public suspend fun countUsedEmojisOfUserInGuild(user: User, guild: Guild): Map<CustomEmoji, Long>
}

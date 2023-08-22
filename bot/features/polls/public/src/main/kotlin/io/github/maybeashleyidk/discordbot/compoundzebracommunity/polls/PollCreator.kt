package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import javax.annotation.CheckReturnValue

public interface PollCreator {

	public interface PollBuilder {

		public val optionsSelectMenuCustomId: String
		public val closeButtonCustomId: String

		public fun addOption(label: String): String

		public fun open()
	}

	@CheckReturnValue
	public fun create(authorId: Long, description: String): PollBuilder
}

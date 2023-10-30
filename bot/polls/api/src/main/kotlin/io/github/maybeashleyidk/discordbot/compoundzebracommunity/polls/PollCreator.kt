package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

public interface PollCreator {

	public interface PollBuilder {

		public val optionsSelectMenuCustomId: String
		public val closeButtonCustomId: String

		public fun addOption(label: String): String

		public fun open()
	}

	public fun create(authorId: Long, description: String): PollBuilder
}

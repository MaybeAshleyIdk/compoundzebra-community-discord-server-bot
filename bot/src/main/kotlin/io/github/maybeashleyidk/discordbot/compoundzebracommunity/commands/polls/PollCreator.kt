package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls

import javax.annotation.CheckReturnValue

internal interface PollCreator {

	interface PollBuilder {

		val optionsSelectMenuCustomId: String
		val closeButtonCustomId: String

		fun addOption(label: String): String

		fun open()
	}

	@CheckReturnValue
	fun create(authorId: Long, description: String): PollBuilder
}

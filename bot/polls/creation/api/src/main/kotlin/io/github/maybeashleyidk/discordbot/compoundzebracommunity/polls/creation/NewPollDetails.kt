package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption

public data class NewPollDetails(
	val id: PollId,
	val authorId: Long,
	val description: PollDescription,
	val options: List<PollOption>,
) {

	public fun createMessageContent(pollStrings: LanguageStrings.Poll): String {
		val pollDetails =
			PollDetails(
				authorId = this.authorId,
				description = this.description,
				options = this.options,
				votes = emptyMap(),
				closerMemberId = null,
			)

		return pollDetails.createMessageContent(pollStrings)
	}
}

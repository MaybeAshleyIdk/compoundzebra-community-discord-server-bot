package io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionValue
import net.dv8tion.jda.api.entities.ISnowflake

public data class PollDetails(
	val authorId: Long,
	val description: PollDescription,
	val options: List<PollOption>,
	/** key is the member ID */
	val votes: Map<Long, PollOptionValue>,
	val closerMemberId: Long?,
) {

	init {
		require(this.votes.values.all(this::containsOptionWithValue))
	}

	public fun createMessageContent(pollStrings: LanguageStrings.Poll): String {
		val sb = StringBuilder()

		sb.append(pollStrings.title(this@PollDetails.authorId.snowflakeToString()))
		sb.append("\n\n> ")
		sb.append(this@PollDetails.description.toString().replace("\n", "\n> "))

		for (option: PollOption in this.options) {
			val s: String = pollStrings.option(option.label, this@PollDetails.countVotesOfOption(option.value))
			sb.append('\n')
			sb.append(s)
		}

		if (this.closerMemberId != null) {
			sb.append("\n\n")
			sb.append(pollStrings.closedMessage(this@PollDetails.closerMemberId.snowflakeToString()))
		}

		return sb.toString()
	}

	public fun withVoter(voterMemberId: Long, optionValue: PollOptionValue): PollDetails {
		require(this.containsOptionWithValue(optionValue))

		return this.copy(votes = this.votes.plus(voterMemberId to optionValue))
	}

	public fun withCloserMember(memberId: Long): PollDetails {
		return this.copy(closerMemberId = memberId)
	}

	private fun countVotesOfOption(optionValue: PollOptionValue): Int {
		return this@PollDetails.votes
			.count { (_: Long, value: PollOptionValue) ->
				value == optionValue
			}
	}

	private fun containsOptionWithValue(optionValue: PollOptionValue): Boolean {
		return this.options
			.any { option: PollOption ->
				option.value == optionValue
			}
	}
}

private fun Long.snowflakeToString(): String {
	return ISnowflake { this@snowflakeToString }.id // hacky way to do that lmao
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.LanguageStrings
import net.dv8tion.jda.api.entities.ISnowflake
import javax.annotation.CheckReturnValue

internal data class PollOption(
	val value: String,
	val label: String,
)

internal data class PollDetails(
	val authorId: Long,
	val description: String,
	val options: List<PollOption>,
	/** member ID -> poll option value */
	val voters: Map<Long, String>,
	val closedByUserId: Long?,
) {

	init {
		require(this.voters.values.all(this::containsOptionWithValue))
	}

	@CheckReturnValue
	fun createMessageContent(pollStrings: LanguageStrings.Poll): String {
		return buildString {
			this@buildString.append(pollStrings.title(this@PollDetails.authorId.snowflakeToString()))
			this@buildString.append("\n\n> ")
			this@buildString.append(this@PollDetails.description.replace("\n", "\n> "))

			this@PollDetails.options.forEach { option: PollOption ->
				val s: String = pollStrings.option(option.label, this@PollDetails.countVotesOfOptions(option.value))
				this@buildString.append('\n')
				this@buildString.append(s)
			}

			if (this@PollDetails.closedByUserId != null) {
				this@buildString.append("\n\n")
				this@buildString.append(pollStrings.closedMessage(this@PollDetails.closedByUserId.snowflakeToString()))
			}
		}
	}

	@CheckReturnValue
	fun countVotesOfOptions(optionValue: String): Int {
		return this@PollDetails.voters
			.count { (_: Long, value: String) ->
				value == optionValue
			}
	}

	@CheckReturnValue
	fun withVoter(voterId: Long, optionValue: String): PollDetails {
		require(this.containsOptionWithValue(optionValue))

		return this.copy(voters = this.voters.plus(voterId to optionValue))
	}

	@CheckReturnValue
	fun closedBy(userId: Long): PollDetails {
		return this.copy(closedByUserId = userId)
	}

	@CheckReturnValue
	private fun containsOptionWithValue(optionValue: String): Boolean {
		return this.options
			.any { option: PollOption ->
				option.value == optionValue
			}
	}
}

@CheckReturnValue
private fun Long.snowflakeToString(): String {
	return ISnowflake { this@snowflakeToString }.id // hacky way to do that lmao
}

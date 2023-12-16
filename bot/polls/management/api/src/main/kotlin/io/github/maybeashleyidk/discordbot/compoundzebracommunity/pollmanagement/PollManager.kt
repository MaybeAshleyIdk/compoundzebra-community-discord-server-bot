package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionValue
import net.dv8tion.jda.api.entities.Member

public interface PollManager {

	public sealed class CloseResult {

		public data object Denied : CloseResult()

		/**
		 * [pollDetails] will be `null` if the poll was already closed.
		 */
		public data class Closed(val pollDetails: PollDetails?) : CloseResult()
	}

	public suspend fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): NewPollDetails

	public suspend fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails?

	/**
	 * Returns `null` if the poll with the ID [pollId] doesn't exist.
	 */
	public suspend fun voteOption(pollId: PollId, voterMember: Member, optionValue: PollOptionValue): PollDetails?

	public suspend fun closePollUnrestricted(pollId: PollId, closerMember: Member)

	public suspend fun closePollIfAllowed(pollId: PollId, closerMember: Member): CloseResult
}

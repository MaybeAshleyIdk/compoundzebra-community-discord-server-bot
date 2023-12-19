package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionValue
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

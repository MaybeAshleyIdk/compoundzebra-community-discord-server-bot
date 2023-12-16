package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmodification

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionValue
import net.dv8tion.jda.api.entities.Member

public interface PollModifier {

	public sealed class CloseResult {

		public data object Denied : CloseResult()

		/**
		 * [pollDetails] will be `null` if the poll was already closed.
		 */
		public data class Closed(val pollDetails: PollDetails?) : CloseResult()
	}

	/**
	 * Returns `null` if the poll with the ID [pollId] doesn't exist.
	 */
	public suspend fun voteOption(pollId: PollId, voterMember: Member, optionValue: PollOptionValue): PollDetails?

	public suspend fun closePollIfAllowed(pollId: PollId, member: Member): CloseResult
}

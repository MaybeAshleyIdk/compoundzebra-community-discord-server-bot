package io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.poll.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polldescription.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollid.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polloption.PollOptionValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class PollManagerImpl @Inject constructor(
	private val pollIdGenerator: PollIdGenerator,
	private val logger: Logger,
) : PollManager {

	private val activePolls: MutableMap<PollId, PollDetails> = HashMap()

	override fun openNewPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
	): NewPollDetails {
		require(optionLabels.size >= 2) {
			"At least two options must be given"
		}

		val options: List<PollOption> = optionLabels
			.mapIndexed { index: Int, optionLabel: PollOptionLabel ->
				PollOption(
					value = PollOptionValue.ofString("pollOption[$index]"),
					label = optionLabel,
				)
			}

		val newPollDetails =
			NewPollDetails(
				id = this.pollIdGenerator.generate(),
				authorId = author.idLong,
				description = description,
				options = options,
			)

		synchronized(this.activePolls) {
			this.activePolls[newPollDetails.id] =
				PollDetails(
					authorId = newPollDetails.authorId,
					description = newPollDetails.description,
					options = newPollDetails.options,
					votes = emptyMap(),
					closerMemberId = null,
				)
		}

		val logMsg: String = "New poll with ID ${newPollDetails.id} opened " +
			"by member ${author.user.name.quoted()} (${author.id})"
		this.logger.logInfo(logMsg)

		return newPollDetails
	}

	override fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails? {
		return synchronized(this.activePolls) {
			this.activePolls[pollId]
		}
	}

	override fun voteOption(pollId: PollId, voterMember: Member, optionValue: PollOptionValue): PollDetails? {
		val newDetails: PollDetails? =
			synchronized(this.activePolls) {
				val oldDetails: PollDetails = this.activePolls[pollId]
					?: return@synchronized null

				val newDetails: PollDetails = oldDetails.withVoter(voterMember.idLong, optionValue)

				this.activePolls[pollId] = newDetails

				newDetails
			}

		// note: we consciously do NOT log the option that was voted on to respect user privacy
		if (newDetails != null) {
			val logMsg: String = "Poll with ID $pollId was voted on " +
				"by member ${voterMember.user.name.quoted()} (${voterMember.id})"
			this.logger.logInfo(logMsg)
		} else {
			val logMsg: String = "Poll with ID $pollId was attempted to be voted on " +
				"by member ${voterMember.user.name.quoted()} (${voterMember.id}), but it failed " +
				"due to the poll already being closed"
			this.logger.logWarning(logMsg)
		}

		return newDetails
	}

	override fun closePollUnrestricted(pollId: PollId, closerMember: Member) {
		synchronized(this.activePolls) {
			this.closePollUnsynchronized(pollId)
		}

		val logMsg: String = "Poll with ID $pollId was closed " +
			"by member ${closerMember.user.name.quoted()} (${closerMember.id}) without checking for permissions"
		this.logger.logWarning(logMsg)
	}

	override fun closePollIfAllowed(pollId: PollId, closerMember: Member): PollManager.CloseResult {
		val result: PollManager.CloseResult =
			synchronized(this.activePolls) {
				val pollDetails: PollDetails = this.activePolls[pollId]
					?: return@synchronized PollManager.CloseResult.Closed(pollDetails = null)

				if (!(pollDetails.isAllowedToBeClosedBy(closerMember))) {
					return@synchronized PollManager.CloseResult.Denied
				}

				this.closePollUnsynchronized(pollId)

				val closedPollDetails: PollDetails = pollDetails.withCloserMember(closerMember.idLong)
				PollManager.CloseResult.Closed(closedPollDetails)
			}

		when (result) {
			is PollManager.CloseResult.Denied -> {
				val logMsg: String = "Poll with ID $pollId was attempted to be closed " +
					"by member ${closerMember.user.name.quoted()} (${closerMember.id}), but it failed " +
					"due to insufficient permissions"
				this.logger.logInfo(logMsg)
			}

			is PollManager.CloseResult.Closed -> {
				if (result.pollDetails != null) {
					val logMsg: String = "Poll with ID $pollId was closed " +
						"by member ${closerMember.user.name.quoted()} (${closerMember.id})"
					this.logger.logInfo(logMsg)
				} else {
					val logMsg: String =
						"Poll with ID $pollId was attempted to be closed " +
							"by member ${closerMember.user.name.quoted()} (${closerMember.id}), but it failed " +
							"due to the poll already being closed"
					this.logger.logWarning(logMsg)
				}
			}
		}

		return result
	}

	private fun closePollUnsynchronized(pollId: PollId) {
		this.activePolls.remove(pollId)
	}
}

private fun PollDetails.isAllowedToBeClosedBy(member: Member): Boolean {
	return (member.idLong == this.authorId) || member.isOwner || member.hasPermission(Permission.ADMINISTRATOR)
}

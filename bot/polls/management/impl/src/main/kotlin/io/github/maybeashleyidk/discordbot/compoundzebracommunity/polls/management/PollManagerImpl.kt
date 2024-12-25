package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOption
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVal
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.strings.quoted
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member

public class PollManagerImpl(private val logger: Logger) : PollManager {

	private val pollIdGenerator: PollIdGenerator = PollIdGenerator()

	private val activePolls: AtomicVal<MutableMap<PollId, PollDetails>> = AtomicVal(HashMap())

	override suspend fun openNewPoll(
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

		this.activePolls.visit { activePolls: MutableMap<PollId, PollDetails> ->
			activePolls[newPollDetails.id] =
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

	override suspend fun getPollDetailsByIdOrNull(pollId: PollId): PollDetails? {
		return this.activePolls.get()[pollId]
	}

	override suspend fun voteOption(pollId: PollId, voterMember: Member, optionValue: PollOptionValue): PollDetails? {
		val newDetails: PollDetails? =
			this.activePolls.visit { activePolls: MutableMap<PollId, PollDetails> ->
				val oldDetails: PollDetails = activePolls[pollId]
					?: return@visit null

				val newDetails: PollDetails = oldDetails.withVoter(voterMember.idLong, optionValue)

				activePolls[pollId] = newDetails

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

	override suspend fun closePollUnrestricted(pollId: PollId, closerMember: Member) {
		this.activePolls.visit { activePolls: MutableMap<PollId, PollDetails> ->
			activePolls.remove(pollId)
		}

		val logMsg: String = "Poll with ID $pollId was closed " +
			"by member ${closerMember.user.name.quoted()} (${closerMember.id}) without checking for permissions"
		this.logger.logWarning(logMsg)
	}

	override suspend fun closePollIfAllowed(pollId: PollId, closerMember: Member): PollManager.CloseResult {
		val result: PollManager.CloseResult =
			this.activePolls.visit { activePolls: MutableMap<PollId, PollDetails> ->
				val pollDetails: PollDetails = activePolls[pollId]
					?: return@visit PollManager.CloseResult.Closed(pollDetails = null)

				if (!(pollDetails.isAllowedToBeClosedBy(closerMember))) {
					return@visit PollManager.CloseResult.Denied
				}

				activePolls.remove(pollId)

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
}

private fun PollDetails.isAllowedToBeClosedBy(member: Member): Boolean {
	return (member.idLong == this.authorId) || member.isOwner || member.hasPermission(Permission.ADMINISTRATOR)
}

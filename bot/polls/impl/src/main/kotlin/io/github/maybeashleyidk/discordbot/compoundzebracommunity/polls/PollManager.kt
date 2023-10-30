package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class PollManager @Inject constructor(
	private val pollIdGenerator: PollIdGenerator,
	private val logger: Logger,
) : PollCreator, PollHolder {

	private companion object {

		val OPTIONS_SELECT_MENU_COMPONENT_ID_REGEX_PATTERN = Regex("^poll\\[(?<id>[0-9]+)]\\.optionsSelectMenu$")
		val CLOSE_BUTTON_COMPONENT_ID_REGEX_PATTERN = Regex("^poll\\[(?<id>[0-9]+)]\\.closeButton$")
	}

	internal sealed class CloseResult {
		data object WrongComponentId : CloseResult()

		data object InsufficientPermissions : CloseResult()

		data class Closed(val pollDetails: PollDetails?) : CloseResult()
	}

	private val activePolls: MutableMap<PollId, PollDetails> = HashMap()

	override fun create(authorId: Long, description: String): PollCreator.PollBuilder {
		val pollId: PollId = this.pollIdGenerator.generate()
		return PollBuilderImpl(
			pollId,
			pollAuthorId = authorId,
			pollDescription = description,
			pollOpenAction = this::open,
		)
	}

	@Synchronized
	private fun open(pollId: PollId, pollDetails: PollDetails) {
		this.activePolls[pollId] = pollDetails
		this.logger.logInfo("Poll with ID $pollId opened")
	}

	internal fun voteOption(member: Member, optionsSelectMenuComponentId: String, optionValue: String): PollDetails? {
		@Suppress("RemoveRedundantQualifierName")
		val matchResult: MatchResult = PollManager.OPTIONS_SELECT_MENU_COMPONENT_ID_REGEX_PATTERN
			.matchEntire(optionsSelectMenuComponentId)
			?: return null

		val matchGroup: MatchGroup = checkNotNull(matchResult.groups["id"])
		val pollId: PollId = PollId.ofULong(matchGroup.value.toULong())

		return this.voteOption(member.idLong, pollId, optionValue)
	}

	@Synchronized
	private fun voteOption(memberId: Long, pollId: PollId, optionValue: String): PollDetails? {
		val newDetails: PollDetails = (this.activePolls[pollId] ?: return null)
			.withVoter(memberId, optionValue)

		this.activePolls[pollId] = newDetails

		return newDetails
	}

	override fun getPollById(pollId: PollId): PollDetails? {
		return this.activePolls[pollId]
	}

	internal fun close(member: Member, closeButtonComponentId: String): CloseResult {
		@Suppress("RemoveRedundantQualifierName")
		val matchResult: MatchResult = PollManager.CLOSE_BUTTON_COMPONENT_ID_REGEX_PATTERN
			.matchEntire(closeButtonComponentId)
			?: return CloseResult.WrongComponentId

		val matchGroup: MatchGroup = checkNotNull(matchResult.groups["id"])
		val pollId: PollId = PollId.ofULong(matchGroup.value.toULong())

		return this.close(member, pollId)
	}

	@Synchronized
	private fun close(member: Member, pollId: PollId): CloseResult {
		val pollDetails: PollDetails = this.activePolls[pollId]
			?: return CloseResult.Closed(null)

		if (!(pollDetails.isAllowedToBeClosedBy(member))) {
			return CloseResult.InsufficientPermissions
		}

		this.activePolls.remove(pollId)

		this.logger.logInfo("Poll with ID $pollId closed")

		return CloseResult.Closed(pollDetails.closedBy(member.idLong))
	}
}

private fun PollDetails.isAllowedToBeClosedBy(member: Member): Boolean {
	return (member.idLong == this.authorId) || member.isOwner || member.hasPermission(Permission.ADMINISTRATOR)
}

private class PollBuilderImpl(
	private val pollId: PollId,
	private val pollAuthorId: Long,
	private val pollDescription: String,
	private val pollOpenAction: (PollId, PollDetails) -> Unit,
) : PollCreator.PollBuilder {

	private val optionValues: MutableList<PollOption> = ArrayList(8)

	override val optionsSelectMenuCustomId: String = "poll[${this.pollId}].optionsSelectMenu"
	override val closeButtonCustomId: String = "poll[${this.pollId}].closeButton"

	override fun addOption(label: String): String {
		val value = "pollOption[${this.optionValues.size}]"
		this.optionValues.add(PollOption(value, label))
		return value
	}

	override fun open() {
		val pollDetails =
			PollDetails(
				this.pollAuthorId,
				this.pollDescription,
				this.optionValues,
				voters = emptyMap(),
				closedByUserId = null,
			)

		this.pollOpenAction(this.pollId, pollDetails)
	}
}

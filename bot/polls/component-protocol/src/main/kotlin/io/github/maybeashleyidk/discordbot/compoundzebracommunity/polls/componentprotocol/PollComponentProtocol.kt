package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.componentprotocol

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import javax.inject.Inject

public class PollComponentProtocol @Inject constructor() {

	private companion object {
		val COMPONENT_ID_REGEX_PATTERN_OPTIONS_SELECT_MENU = Regex("^poll\\[(?<id>[0-9]+)]\\.optionsSelectMenu$")
		val COMPONENT_ID_REGEX_PATTERN_CLOSE_BUTTON = Regex("^poll\\[(?<id>[0-9]+)]\\.closeButton$")
	}

	public fun mapPollIdToOptionsSelectMenuComponentId(pollId: PollId): String {
		return "poll[$pollId].optionsSelectMenu"
	}

	public fun mapOptionsSelectMenuComponentIdToPollId(componentId: String): PollId? {
		@Suppress("RemoveRedundantQualifierName")
		val matchResult: MatchResult =
			PollComponentProtocol.COMPONENT_ID_REGEX_PATTERN_OPTIONS_SELECT_MENU.matchEntire(componentId)
				?: return null

		val matchGroup: MatchGroup = checkNotNull(matchResult.groups["id"])
		return PollId.ofULong(matchGroup.value.toULong())
	}

	public fun mapPollIdToCloseButtonComponentId(pollId: PollId): String {
		return "poll[$pollId].closeButton"
	}

	public fun mapCloseButtonComponentIdToPollId(componentId: String): PollId? {
		@Suppress("RemoveRedundantQualifierName")
		val matchResult: MatchResult =
			PollComponentProtocol.COMPONENT_ID_REGEX_PATTERN_CLOSE_BUTTON.matchEntire(componentId)
				?: return null

		val matchGroup: MatchGroup = checkNotNull(matchResult.groups["id"])
		return PollId.ofULong(matchGroup.value.toULong())
	}
}

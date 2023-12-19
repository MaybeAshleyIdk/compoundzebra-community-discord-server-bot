package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.messagecreation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.details.PollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import net.dv8tion.jda.api.utils.messages.MessageCreateData

public interface PollMessageCreator {

	public fun createMessageData(pollId: PollId, pollDetails: PollDetails): MessageCreateData
}

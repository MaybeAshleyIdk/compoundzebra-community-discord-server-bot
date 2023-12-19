package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.sending

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description.PollDescription
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.messagecreation.PollMessageCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.newpolldetails.NewPollDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option.PollOptionLabel
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import javax.inject.Inject

public class PollSenderImpl @Inject constructor(
	private val pollCreator: PollCreator,
	private val pollMessageCreator: PollMessageCreator,
) : PollSender {

	override suspend fun createAndSendPoll(
		author: Member,
		description: PollDescription,
		optionLabels: List<PollOptionLabel>,
		targetChannel: GuildMessageChannel,
	) {
		val newPollDetails: NewPollDetails = this.pollCreator.openNewPoll(author, description, optionLabels)

		try {
			val messageCreateData: MessageCreateData = this.pollMessageCreator
				.createMessageData(
					newPollDetails.id,
					newPollDetails.toEmptyPollDetails(),
				)

			targetChannel.sendMessage(messageCreateData).await()
		} catch (e: Throwable) {
			try {
				this.pollCreator.closePoll(newPollDetails.id, closerMember = author)
			} catch (closeException: Throwable) {
				e.addSuppressed(closeException)
			}

			throw e
		}
	}
}

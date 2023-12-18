package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollsCreationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollsEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollsHoldingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollsManagementModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollsModificationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.sending.PollsSendingModule

@Module(
	includes = [
		PollsManagementModule::class,
		PollsCreationModule::class,
		PollsHoldingModule::class,
		PollsModificationModule::class,
		PollsEventHandlingModule::class,
		PollsSendingModule::class,
	],
)
public object PollsModule

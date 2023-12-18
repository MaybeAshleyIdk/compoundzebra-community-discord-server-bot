package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollsCreationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollsEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollsHoldingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollsManagementModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollsModificationModule

@Module(
	includes = [
		PollsManagementModule::class,
		PollsCreationModule::class,
		PollsHoldingModule::class,
		PollsModificationModule::class,
		PollsEventHandlingModule::class,
	],
)
public object PollsModule

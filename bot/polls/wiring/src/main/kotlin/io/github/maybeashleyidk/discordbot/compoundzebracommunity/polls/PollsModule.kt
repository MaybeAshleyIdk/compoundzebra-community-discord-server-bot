package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHoldingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManagementModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollModificationModule

@Module(
	includes = [
		PollManagementModule::class,
		PollCreationModule::class,
		PollHoldingModule::class,
		PollModificationModule::class,
		PollEventHandlingModule::class,
	],
)
public object PollsModule

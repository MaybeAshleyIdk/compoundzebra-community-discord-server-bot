package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollcreation.PollCreationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening.PollEventListeningModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollholding.PollHoldingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmanagement.PollManagementModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.pollmodification.PollModificationModule

@Module(
	includes = [
		PollManagementModule::class,
		PollCreationModule::class,
		PollHoldingModule::class,
		PollModificationModule::class,
		PollEventListeningModule::class,
	],
)
public object PollsModule

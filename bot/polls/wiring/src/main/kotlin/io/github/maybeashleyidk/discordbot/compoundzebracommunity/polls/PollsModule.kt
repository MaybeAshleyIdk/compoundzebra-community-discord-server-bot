package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollsCreationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollsEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollsHoldingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollsModificationModule
import javax.inject.Singleton

@Module(
	includes = [
		PollsCreationModule::class,
		PollsHoldingModule::class,
		PollsModificationModule::class,
		PollsEventHandlingModule::class,
	],
)
public object PollsModule {

	@Provides
	@Singleton
	internal fun providePollManager(logger: Logger): PollManager {
		return PollManagerImpl(logger)
	}
}

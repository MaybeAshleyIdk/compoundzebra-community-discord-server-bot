package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollManagerCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollsEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollManagerHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollsModificationModule
import javax.inject.Singleton

@Module(
	includes = [
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

	@Provides
	internal fun providePollCreator(pollManager: PollManager): PollCreator {
		return PollManagerCreator(pollManager)
	}

	@Provides
	internal fun providePollHolder(pollManager: PollManager): PollHolder {
		return PollManagerHolder(pollManager)
	}
}

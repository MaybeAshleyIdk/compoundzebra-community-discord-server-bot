package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollManagerCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollEventHandlerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollManagerHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management.PollManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollManagerModifier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.modification.PollModifier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.Provider
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.getValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope

public class PollsModule(
	scope: DiScope,
	logger: Provider<Logger>,
	configSupplier: Provider<ConfigSupplier>,
) : DiModule(scope) {

	private val logger: Logger by logger
	private val configSupplier: ConfigSupplier by configSupplier

	private val pollManager: PollManager by this.singleton {
		PollManagerImpl(this.logger)
	}

	private val pollModifier: PollModifier
		get() {
			return PollManagerModifier(this.pollManager)
		}

	public val pollCreator: PollCreator
		get() {
			return PollManagerCreator(this.pollManager)
		}

	public val pollHolder: PollHolder
		get() {
			return PollManagerHolder(this.pollManager)
		}

	public val pollEventHandler: PollEventHandler
		get() {
			return PollEventHandlerImpl(this.pollModifier, this.configSupplier)
		}
}

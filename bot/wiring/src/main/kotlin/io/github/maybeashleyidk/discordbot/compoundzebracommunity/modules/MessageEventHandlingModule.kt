package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandsMessageEventHandlingImplModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandlerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediatorImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path

internal class MessageEventHandlingModule(
	scope: DiScope,
	private val configSupplier: ConfigSupplier,
	configFilePath: Path,
	// emojiStatsManager: EmojiStatsManager,
	pollCreator: PollCreator,
	pollHolder: PollHolder,
	selfTimeoutService: SelfTimeoutService,
	shutdownRequester: ShutdownRequester,
	botEnvironmentType: BotEnvironmentType,
	private val logger: Logger,
) : DiModule(scope) {

	private val commandsMessageEventHandlingImplModule: CommandsMessageEventHandlingImplModule by this.reusable {
		CommandsMessageEventHandlingImplModule(
			scope,
			this.configSupplier,
			configFilePath,
			// emojiStatsManager,
			pollCreator,
			pollHolder,
			selfTimeoutService,
			shutdownRequester,
			botEnvironmentType,
			this.logger,
		)
	}

	private val conditionalMessageEventHandler: ConditionalMessageEventHandler
		get() {
			return ConditionalMessageEventHandlerImpl(this.configSupplier, this.logger)
		}

	val messageEventHandlerMediator: MessageEventHandlerMediator
		get() {
			return MessageEventHandlerMediatorImpl(
				this.commandsMessageEventHandlingImplModule.commandMessageEventHandlerImpl,
				this.conditionalMessageEventHandler,
			)
		}
}

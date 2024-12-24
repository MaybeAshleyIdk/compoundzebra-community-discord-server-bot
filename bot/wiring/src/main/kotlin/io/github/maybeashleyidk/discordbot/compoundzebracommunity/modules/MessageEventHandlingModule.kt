package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandMessageEventHandler
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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path

@Module
internal object MessageEventHandlingModule {

	@Provides
	@Reusable
	internal fun provideCommandsMessageEventHandlingImplModule(
		scope: DiScope,
		configSupplier: ConfigSupplier,
		configFilePath: Path,
		// emojiStatsManager: EmojiStatsManager,
		pollCreator: PollCreator,
		pollHolder: PollHolder,
		selfTimeoutService: SelfTimeoutService,
		shutdownRequester: ShutdownRequester,
		botEnvironmentType: BotEnvironmentType,
		logger: Logger,
	): CommandsMessageEventHandlingImplModule {
		return CommandsMessageEventHandlingImplModule(
			scope,
			configSupplier,
			configFilePath,
			// emojiStatsManager,
			pollCreator,
			pollHolder,
			selfTimeoutService,
			shutdownRequester,
			botEnvironmentType,
			logger,
		)
	}

	@Provides
	internal fun provideCommandMessageEventHandler(
		commandsMessageEventHandlingImplModule: CommandsMessageEventHandlingImplModule,
	): CommandMessageEventHandler {
		return commandsMessageEventHandlingImplModule.commandMessageEventHandlerImpl
	}

	@Provides
	fun provideConditionalMessageEventHandler(
		configSupplier: ConfigSupplier,
		logger: Logger,
	): ConditionalMessageEventHandler {
		return ConditionalMessageEventHandlerImpl(configSupplier, logger)
	}

	@Provides
	fun provideMessageEventHandlerMediator(
		commandMessageEventHandler: CommandMessageEventHandler,
		conditionalMessageEventHandler: ConditionalMessageEventHandler,
	): MessageEventHandlerMediator {
		return MessageEventHandlerMediatorImpl(commandMessageEventHandler, conditionalMessageEventHandler)
	}
}

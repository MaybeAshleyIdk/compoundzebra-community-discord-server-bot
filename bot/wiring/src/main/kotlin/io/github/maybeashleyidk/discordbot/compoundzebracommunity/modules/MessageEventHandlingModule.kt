package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandsMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandlerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediationModule

@Module(
	includes = [
		CommandsMessageEventHandlingModule::class,
		MessageEventHandlerMediationModule::class,
	],
)
internal object MessageEventHandlingModule {

	@Provides
	fun provideConditionalMessageEventHandler(
		configSupplier: ConfigSupplier,
		logger: Logger,
	): ConditionalMessageEventHandler {
		return ConditionalMessageEventHandlerImpl(configSupplier, logger)
	}
}

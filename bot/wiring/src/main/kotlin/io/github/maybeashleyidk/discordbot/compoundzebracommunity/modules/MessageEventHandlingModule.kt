package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler.CommandMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages.ConditionalMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediator.MessageEventHandlerMediationModule

@Module(
	includes = [
		CommandMessageEventHandlingModule::class,
		ConditionalMessageEventHandlingModule::class,
		MessageEventHandlerMediationModule::class,
	],
)
internal object MessageEventHandlingModule

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler.CommandMessageEventHandlerModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages.ConditionalMessagesModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediator.MessageEventHandlerMediatorModule

@Module(
	includes = [
		CommandMessageEventHandlerModule::class,
		ConditionalMessagesModule::class,
		MessageEventHandlerMediatorModule::class,
	],
)
internal object MessageEventHandlingModule

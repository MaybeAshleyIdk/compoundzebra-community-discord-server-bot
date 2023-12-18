package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandsMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediationModule

@Module(
	includes = [
		CommandsMessageEventHandlingModule::class,
		ConditionalMessageEventHandlingModule::class,
		MessageEventHandlerMediationModule::class,
	],
)
internal object MessageEventHandlingModule

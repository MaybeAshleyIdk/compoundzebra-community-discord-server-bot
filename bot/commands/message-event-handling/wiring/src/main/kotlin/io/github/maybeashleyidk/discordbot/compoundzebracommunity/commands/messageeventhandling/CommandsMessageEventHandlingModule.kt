package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import dagger.Binds
import dagger.Module

@Module(
	includes = [
		CommandsMessageEventHandlingImplModule::class,
		CommandsMessageEventHandlingModule.Binding::class,
	],
)
public object CommandsMessageEventHandlingModule {

	@Module
	internal interface Binding {

		@Binds
		fun bindCommandMessageEventHandler(impl: CommandMessageEventHandlerImpl): CommandMessageEventHandler
	}
}

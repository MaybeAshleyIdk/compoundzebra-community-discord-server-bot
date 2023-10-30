package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandler

import dagger.Binds
import dagger.Module

@Module(
	includes = [
		CommandMessageEventHandlerModule.Binding::class,
		ImplCommandMessageEventHandlerModule::class,
	],
)
public object CommandMessageEventHandlerModule {

	@Module
	internal interface Binding {

		@Binds
		fun bindCommandMessageEventHandler(impl: CommandMessageEventHandlerImpl): CommandMessageEventHandler
	}
}

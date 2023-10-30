package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling

import dagger.Binds
import dagger.Module

@Module(
	includes = [
		CommandMessageEventHandlingModule.Binding::class,
		ImplCommandMessageEventHandlerModule::class,
	],
)
public object CommandMessageEventHandlingModule {

	@Module
	internal interface Binding {

		@Binds
		fun bindCommandMessageEventHandler(impl: CommandMessageEventHandlerImpl): CommandMessageEventHandler
	}
}

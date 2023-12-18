package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import dagger.Binds
import dagger.Module

@Module(
	includes = [
		CommandMessageEventHandlingModule.Binding::class,
		CommandMessageEventHandlingImplModule::class,
	],
)
public object CommandMessageEventHandlingModule {

	@Module
	internal interface Binding {

		@Binds
		fun bindCommandMessageEventHandler(impl: CommandMessageEventHandlerImpl): CommandMessageEventHandler
	}
}

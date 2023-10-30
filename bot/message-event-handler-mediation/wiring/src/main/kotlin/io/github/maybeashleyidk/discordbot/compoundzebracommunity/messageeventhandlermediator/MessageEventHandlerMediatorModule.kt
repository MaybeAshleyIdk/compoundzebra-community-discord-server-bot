package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediator

import dagger.Binds
import dagger.Module

@Module(includes = [MessageEventHandlerMediatorModule.Bindings::class])
public object MessageEventHandlerMediatorModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindMessageEventHandlerMediator(
			messageEventHandlerMediatorImpl: MessageEventHandlerMediatorImpl,
		): MessageEventHandlerMediator
	}
}

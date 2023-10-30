package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import dagger.Binds
import dagger.Module

@Module(includes = [MessageEventHandlerMediationModule.Bindings::class])
public object MessageEventHandlerMediationModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindMessageEventHandlerMediator(
			messageEventHandlerMediatorImpl: MessageEventHandlerMediatorImpl,
		): MessageEventHandlerMediator
	}
}

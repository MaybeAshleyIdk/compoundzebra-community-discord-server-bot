package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages

import dagger.Binds
import dagger.Module

@Module(includes = [ConditionalMessagesModule.Bindings::class])
public object ConditionalMessagesModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConditionalMessageEventHandler(impl: ConditionalMessageEventHandlerImpl): ConditionalMessageEventHandler
	}
}

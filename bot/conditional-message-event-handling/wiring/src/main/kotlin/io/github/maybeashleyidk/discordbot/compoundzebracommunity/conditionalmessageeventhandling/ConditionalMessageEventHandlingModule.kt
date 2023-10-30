package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling

import dagger.Binds
import dagger.Module

@Module(includes = [ConditionalMessageEventHandlingModule.Bindings::class])
public object ConditionalMessageEventHandlingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindConditionalMessageEventHandler(impl: ConditionalMessageEventHandlerImpl): ConditionalMessageEventHandler
	}
}

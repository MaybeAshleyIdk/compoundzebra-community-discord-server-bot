package io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling

import dagger.Binds
import dagger.Module

@Module(includes = [PrivateMessageEventHandlingModule.Bindings::class])
public object PrivateMessageEventHandlingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPrivateMessageEventHandler(impl: PrivateMessageEventHandlerImpl): PrivateMessageEventHandler
	}
}

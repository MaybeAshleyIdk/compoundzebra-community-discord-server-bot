package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.sending

import dagger.Binds
import dagger.Module

@Module(includes = [PollsSendingModule.Bindings::class])
public object PollsSendingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPollSender(impl: PollSenderImpl): PollSender
	}
}

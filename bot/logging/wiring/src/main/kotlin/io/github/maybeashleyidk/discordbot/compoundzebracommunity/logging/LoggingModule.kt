package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging

import dagger.Binds
import dagger.Module

@Module(includes = [LoggingModule.Bindings::class])
public object LoggingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindLogger(stderrLogger: StderrLogger): Logger
	}
}

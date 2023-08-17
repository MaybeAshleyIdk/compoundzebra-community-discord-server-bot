package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.di

import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.impl.SimpleLogFormatter
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.impl.StderrLogWriter
import javax.inject.Singleton

@Module
public object LoggingModule {

	@Provides
	@Singleton
	public fun provideLogger(): Logger {
		return Logger(
			logFormatter = SimpleLogFormatter(),
			logWriter = StderrLogWriter,
		)
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown

import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistrationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownManagerEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequestingModule
import net.dv8tion.jda.api.JDA
import java.util.function.Supplier
import javax.inject.Provider
import javax.inject.Singleton

@Module(
	includes = [
		ShutdownCallbackRegistrationModule::class,
		ShutdownRequestingModule::class,
	],
)
public object ShutdownModule {

	@Provides
	@Singleton
	internal fun provideShutdownManager(jdaProvider: Provider<JDA>, logger: Logger): ShutdownManager {
		return ShutdownManagerImpl(Supplier(jdaProvider::get), logger)
	}

	@Provides
	internal fun provideShutdownEventHandler(shutdownManager: ShutdownManager): ShutdownEventHandler {
		return ShutdownManagerEventHandler(shutdownManager)
	}
}

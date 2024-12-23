package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.EventListeningModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.StderrLogger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.ConfigModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.MessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutServiceImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.ShutdownModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.StorageModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path
import javax.inject.Provider
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Module(
	includes = [
		MessageEventHandlingModule::class,
		PrivateMessageEventHandlingModule::class,
		EventListeningModule::class,
	],
)
internal object BotModule {

	@Provides
	@Singleton
	fun provideDiScope(): DiScope {
		return DiScope()
	}

	@Provides
	fun provideLogger(): Logger {
		return StderrLogger()
	}

	@Provides
	@Reusable
	fun provideMoshi(): Moshi {
		return Moshi.Builder()
			.build()
	}

	@Provides
	@Reusable
	fun provideShutdownModule(scope: DiScope, jda: Provider<Jda>, logger: Provider<Logger>): ShutdownModule {
		return ShutdownModule(scope, jda, logger)
	}

	@Provides
	fun provideShutdownEventHandler(shutdownModule: ShutdownModule): ShutdownEventHandler {
		return shutdownModule.shutdownEventHandler
	}

	@Provides
	fun provideShutdownCallbackRegistry(shutdownModule: ShutdownModule): ShutdownCallbackRegistry {
		return shutdownModule.shutdownCallbackRegistry
	}

	@Provides
	fun provideShutdownRequester(shutdownModule: ShutdownModule): ShutdownRequester {
		return shutdownModule.shutdownRequester
	}

	@Provides
	@Reusable
	fun provideConfigModule(
		scope: DiScope,
		moshi: Provider<Moshi>,
		botEnvironmentType: Provider<BotEnvironmentType>,
		configFilePath: Provider<Path>,
		logger: Provider<Logger>,
	): ConfigModule {
		return ConfigModule(scope, moshi, botEnvironmentType, configFilePath, logger)
	}

	@Provides
	fun provideConfigSupplier(configModule: ConfigModule): ConfigSupplier {
		return configModule.configSupplier
	}

	@Provides
	@Reusable
	fun provideStorageModule(
		scope: DiScope,
		shutdownCallbackRegistry: Provider<ShutdownCallbackRegistry>,
		shutdownRequester: Provider<ShutdownRequester>,
		logger: Provider<Logger>,
	): StorageModule {
		return StorageModule(scope, shutdownCallbackRegistry, shutdownRequester, logger)
	}

	@Provides
	fun provideDatabase(storageModule: StorageModule): Database {
		return storageModule.database
	}

	@Provides
	@Reusable
	fun providePollsModule(
		scope: DiScope,
		logger: Provider<Logger>,
		configSupplier: Provider<ConfigSupplier>,
	): PollsModule {
		return PollsModule(scope, logger, configSupplier)
	}

	@Provides
	fun providePollCreator(pollsModule: PollsModule): PollCreator {
		return pollsModule.pollCreator
	}

	@Provides
	fun providePollHolder(pollsModule: PollsModule): PollHolder {
		return pollsModule.pollHolder
	}

	@Provides
	fun providePollEventHandler(pollsModule: PollsModule): PollEventHandler {
		return pollsModule.pollEventHandler
	}

	@Provides
	fun provideEmojiStatsManager(logger: Logger): EmojiStatsManager {
		return EmojiStatsManagerImpl(logger)
	}

	@Provides
	fun provideSelfTimeoutService(logger: Logger): SelfTimeoutService {
		return SelfTimeoutServiceImpl(logger)
	}

	@Provides
	@Singleton
	fun provideJda(jdaFactory: JdaFactory): Jda {
		return jdaFactory.create()
	}
}

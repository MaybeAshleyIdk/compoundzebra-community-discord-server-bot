package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListenerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.StderrLogger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.ConfigModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.MessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandlerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutServiceImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.ShutdownModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.StorageModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import java.util.function.Supplier
import javax.inject.Provider
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Module(
	includes = [
		MessageEventHandlingModule::class,
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
	fun provideShutdownModule(scope: DiScope, jdaProvider: Provider<Jda>, logger: Logger): ShutdownModule {
		return ShutdownModule(scope, Supplier(jdaProvider::get), logger)
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
		moshi: Moshi,
		botEnvironmentType: BotEnvironmentType,
		configFilePath: Path,
		logger: Logger,
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
		shutdownCallbackRegistry: ShutdownCallbackRegistry,
		shutdownRequester: ShutdownRequester,
		logger: Logger,
	): StorageModule {
		return StorageModule(scope, shutdownCallbackRegistry, shutdownRequester, logger)
	}

	@Provides
	fun provideDatabase(storageModule: StorageModule): Database {
		return storageModule.database
	}

	@Provides
	@Reusable
	fun providePollsModule(scope: DiScope, logger: Logger, configSupplier: ConfigSupplier): PollsModule {
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
	fun providePrivateMessageEventHandler(logger: Logger): PrivateMessageEventHandler {
		return PrivateMessageEventHandlerImpl(logger)
	}

	@Provides
	fun bindMainEventListener(
		logger: Logger,
		shutdownEventHandler: ShutdownEventHandler,
		shutdownCallbackRegistry: ShutdownCallbackRegistry,
		messageEventHandlerMediator: MessageEventHandlerMediator,
		pollEventHandler: PollEventHandler,
		privateMessageEventHandler: PrivateMessageEventHandler,
	): MainEventListener {
		return MainEventListenerImpl(
			logger,
			shutdownEventHandler,
			shutdownCallbackRegistry,
			messageEventHandlerMediator,
			pollEventHandler,
			privateMessageEventHandler,
		)
	}

	@Provides
	fun provideJdaFactory(
		@BotTokenString botTokenString: String,
		initialActivity: Activity,
		mainEventListener: MainEventListener,
	): JdaFactory {
		return JdaFactory(BotToken.ofString(botTokenString), initialActivity, mainEventListener)
	}

	@Provides
	@Singleton
	fun provideJda(jdaFactory: JdaFactory): Jda {
		return jdaFactory.create()
	}
}

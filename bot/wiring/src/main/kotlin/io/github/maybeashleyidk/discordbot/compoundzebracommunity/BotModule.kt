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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.ShutdownModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
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
		PollsModule::class,
		SelfTimeoutModule::class,
		ShutdownModule::class,
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
	fun provideEmojiStatsManager(logger: Logger): EmojiStatsManager {
		return EmojiStatsManagerImpl(logger)
	}

	@Provides
	@Singleton
	fun provideJda(jdaFactory: JdaFactory): Jda {
		return jdaFactory.create()
	}
}

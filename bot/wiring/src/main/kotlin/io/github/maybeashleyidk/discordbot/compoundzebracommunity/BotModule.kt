package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.LoggingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.ConfigModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.MessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.ShutdownModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollsModule
import net.dv8tion.jda.api.hooks.EventListener
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Module(
	includes = [
		BotModule.Bindings::class,
		LoggingModule::class,
		ConfigModule::class,
		EmojiStatsModule::class,
		PollsModule::class,
		ShutdownModule::class,
		MessageEventHandlingModule::class,
	],
)
internal object BotModule {

	@Module
	interface Bindings {

		@Multibinds
		fun multibindEventListeners(): Set<@JvmSuppressWildcards EventListener>
	}

	@Provides
	@Reusable
	fun provideMoshi(): Moshi {
		return Moshi.Builder()
			.build()
	}

	@Provides
	@Singleton
	fun provideJda(jdaFactory: JdaFactory): Jda {
		return jdaFactory.create()
	}
}

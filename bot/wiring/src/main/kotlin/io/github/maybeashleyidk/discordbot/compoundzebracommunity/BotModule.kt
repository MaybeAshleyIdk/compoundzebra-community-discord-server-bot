package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats.EmojiStatsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.EventListeningModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.LoggingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.ConfigModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.MessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.PollsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.ShutdownModule
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Module(
	includes = [
		LoggingModule::class,
		ConfigModule::class,
		EmojiStatsModule::class,
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

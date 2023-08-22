package io.github.maybeashleyidk.discordbot.compoundzebracommunity.di

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.ShutdownAction
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda
import net.dv8tion.jda.api.JDABuilder as JdaBuilder

@Module(
	includes = [
		BotModule.Bindings::class,
	],
)
internal object BotModule {

	@Module
	interface Bindings {

		@Multibinds
		fun multibindEventListeners(): Set<@JvmSuppressWildcards EventListener>

		@Binds
		fun bindShutdownAction(shutdownManager: ShutdownManager): ShutdownAction
	}

	@Provides
	@Reusable
	fun provideMoshi(): Moshi {
		return Moshi.Builder()
			.build()
	}

	@Provides
	@Singleton
	fun provideJda(
		@BotTokenString token: String,
		initialActivity: Activity,
		eventListeners: Set<@JvmSuppressWildcards EventListener>,
	): Jda {
		return JdaBuilder.createDefault(token)
			.setActivity(initialActivity)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT)
			.addEventListeners(*(eventListeners.toTypedArray()))
			.build()
	}
}

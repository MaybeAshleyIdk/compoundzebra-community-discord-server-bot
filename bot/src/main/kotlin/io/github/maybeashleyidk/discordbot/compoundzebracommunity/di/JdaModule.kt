package io.github.maybeashleyidk.discordbot.compoundzebracommunity.di

import dagger.Module
import dagger.Provides
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda
import net.dv8tion.jda.api.JDABuilder as JdaBuilder

@Module
object JdaModule {

	@Provides
	@Singleton
	fun provideJda(
		@BotTokenString token: String,
		initialActivity: Activity,
	): Jda {
		return JdaBuilder.createDefault(token)
			.setActivity(initialActivity)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT)
			.build()
	}
}

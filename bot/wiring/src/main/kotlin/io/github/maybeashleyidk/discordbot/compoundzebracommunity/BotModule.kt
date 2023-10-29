package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import net.dv8tion.jda.api.hooks.EventListener
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Module(includes = [BotModule.Bindings::class])
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

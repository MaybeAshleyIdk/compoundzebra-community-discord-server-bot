package io.github.maybeashleyidk.discordbot.compoundzebracommunity.di

import dagger.BindsInstance
import dagger.Component
import dagger.Lazy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.di.LoggingModule
import net.dv8tion.jda.api.entities.Activity
import javax.annotation.CheckReturnValue
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Component(
	modules = [
		JdaModule::class,
		LoggingModule::class,
		CommandsModule::class,
	],
)
@Singleton
internal interface JdaComponent {

	@get:BotTokenString
	val tokenString: String

	val lazyJda: Lazy<Jda>

	val logger: Logger

	val shutdownManager: ShutdownManager

	@Component.Factory
	@FunctionalInterface
	fun interface Factory {

		@CheckReturnValue
		fun build(
			@BindsInstance @BotTokenString token: String, // Kapt+Dagger does not support value classes
			@BindsInstance initialActivity: Activity,
		): JdaComponent
	}
}

internal val JdaComponent.token: BotToken
	get() {
		return BotToken.ofString(this.tokenString)
	}

@CheckReturnValue
internal fun JdaComponent.Factory.build(
	token: BotToken,
	initialActivity: Activity,
): JdaComponent {
	return this.build(
		token = token.string,
		initialActivity = initialActivity,
	)
}

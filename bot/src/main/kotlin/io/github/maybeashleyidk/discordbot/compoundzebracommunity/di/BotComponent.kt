package io.github.maybeashleyidk.discordbot.compoundzebracommunity.di

import dagger.BindsInstance
import dagger.Component
import dagger.Lazy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.di.LoggingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.di.PollsModule
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import javax.annotation.CheckReturnValue
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Component(
	modules = [
		BotModule::class,
		LoggingModule::class,
		ConfigModule::class,
		PollsModule::class,
		CommandsModule::class,
	],
)
@Singleton
internal interface BotComponent {

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
			@BindsInstance configFilePath: Path,
		): BotComponent
	}
}

internal val BotComponent.token: BotToken
	get() {
		return BotToken.ofString(this.tokenString)
	}

@CheckReturnValue
internal fun BotComponent.Factory.build(
	token: BotToken,
	initialActivity: Activity,
	configFilePath: Path,
): BotComponent {
	return this.build(
		token = token.string,
		initialActivity = initialActivity,
		configFilePath = configFilePath,
	)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import dagger.BindsInstance
import dagger.Component
import dagger.Lazy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait.ShutdownAwaiter
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Component(modules = [BotModule::class])
@Singleton
public interface BotComponent {

	@get:BotTokenString
	public val tokenString: String

	public val lazyJda: Lazy<Jda>

	public val logger: Logger

	public val shutdownAwaiter: ShutdownAwaiter

	@Component.Factory
	@FunctionalInterface
	public fun interface Factory {

		public fun build(
			@BindsInstance environmentType: BotEnvironmentType,
			@BindsInstance @BotTokenString token: String, // KSP+Dagger does not support value classes
			@BindsInstance initialActivity: Activity,
			@BindsInstance configFilePath: Path,
		): BotComponent
	}
}

public val BotComponent.token: BotToken
	get() {
		return BotToken.ofString(this.tokenString)
	}

public fun BotComponent.Factory.build(
	environmentType: BotEnvironmentType,
	token: BotToken,
	initialActivity: Activity,
	configFilePath: Path,
): BotComponent {
	return this.build(
		environmentType = environmentType,
		token = token.toRawString(),
		initialActivity = initialActivity,
		configFilePath = configFilePath,
	)
}

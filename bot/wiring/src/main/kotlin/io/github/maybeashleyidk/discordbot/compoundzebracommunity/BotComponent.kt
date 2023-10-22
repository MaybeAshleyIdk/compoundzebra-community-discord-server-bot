package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import dagger.BindsInstance
import dagger.Component
import dagger.Lazy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.cache.ConfigCacheModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.serialization.ConfigSerializationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.source.ConfigSourceModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplierModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistenermediator.EventListenerMediatorModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.FeaturesModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.EmojiStatsFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollsFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request.ShutdownRequestFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait.ShutdownAwaiter
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait.ShutdownWaitFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.LoggingModule
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

@Component(
	modules = [
		BotModule::class,
		LoggingModule::class,
		ConfigSupplierModule::class,
		ConfigCacheModule::class,
		ConfigSourceModule::class,
		ConfigSerializationModule::class,
		EmojiStatsFeatureModule::class,
		PollsFeatureModule::class,
		ShutdownWaitFeatureModule::class,
		ShutdownRequestFeatureModule::class,
		FeaturesModule::class,
		CommandsModule::class,
		EventListenerMediatorModule::class,
	],
)
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
		token = token.string,
		initialActivity = initialActivity,
		configFilePath = configFilePath,
	)
}

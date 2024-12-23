package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.coinflip.CoinFlipCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.config.GetConfigCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.dev.DevTestCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.magic8ball.Magic8BallCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.polls.PollCreationCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.polls.PollQueryCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.rng.DieRollingCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.rng.RngCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.shutdown.ShutdownCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.sourcecode.SourceCodeCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.creation.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.holding.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutCommand
import java.nio.file.Path

@Module(includes = [BuiltInCommandsModule.Bindings::class])
public object BuiltInCommandsModule {

	@Module
	internal interface Bindings {

		@Multibinds
		fun multibindCommands(): Set<@JvmSuppressWildcards Command>

		@Multibinds
		@DevCommand
		fun multibindDevCommands(): Set<@JvmSuppressWildcards Command>

		@Binds
		@IntoSet
		fun bindDieRollingCommand(dieRollingCommand: DieRollingCommand): Command

		@Binds
		@IntoSet
		fun bindRngCommand(rngCommand: RngCommand): Command

		@Binds
		@IntoSet
		fun bindSelfTimeoutCommand(selfTimeoutCommand: SelfTimeoutCommand): Command

		@Binds
		@IntoSet
		fun bindShutdownCommand(shutdownCommand: ShutdownCommand): Command

		@Binds
		@IntoSet
		fun bindSourceCodeCommand(sourceCodeCommand: SourceCodeCommand): Command
	}

	@Provides
	@IntoSet
	internal fun provideCoinFlipCommand(configSupplier: ConfigSupplier): Command {
		return CoinFlipCommand(configSupplier)
	}

	@Provides
	@DevCommand
	@IntoSet
	internal fun provideDevTestCommand(): Command {
		return DevTestCommand()
	}

	@Provides
	@IntoSet
	internal fun provideGetConfigCommand(configSupplier: ConfigSupplier, configFilePath: Path): Command {
		return GetConfigCommand(configSupplier, configFilePath)
	}

	// disabled for now
	// @Provides
	// @IntoSet
	// internal fun provideEmojiStatsCommand(
	// 	configSupplier: ConfigSupplier,
	// 	emojiStatsManager: EmojiStatsManager,
	// ): Command {
	// 	return EmojiStatsCommand(configSupplier, emojiStatsManager)
	// }

	@Provides
	@IntoSet
	internal fun provideMagic8BallCommand(configSupplier: ConfigSupplier, logger: Logger): Command {
		return Magic8BallCommand(configSupplier, logger)
	}

	@Provides
	@IntoSet
	internal fun providePollCreationCommand(configSupplier: ConfigSupplier, pollCreator: PollCreator): Command {
		return PollCreationCommand(configSupplier, pollCreator)
	}

	@Provides
	@IntoSet
	internal fun providePollQueryCommand(configSupplier: ConfigSupplier, pollHolder: PollHolder): Command {
		return PollQueryCommand(configSupplier, pollHolder)
	}

	@Provides
	@ElementsIntoSet
	internal fun provideCommandsFromDevCommands(
		botEnvironmentType: BotEnvironmentType,
		@DevCommand devCommands: Set<@JvmSuppressWildcards Command>,
	): Set<@JvmSuppressWildcards Command> {
		return when (botEnvironmentType) {
			BotEnvironmentType.DEVELOPMENT -> devCommands
			BotEnvironmentType.PRODUCTION -> emptySet()
		}
	}
}

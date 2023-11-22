package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.coinflip.CoinFlipCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.config.GetConfigCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.dev.DevTestCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.magic8ball.Magic8BallCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.polls.PollCreationCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.polls.PollQueryCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.rng.DieRollingCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.rng.RngCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.shutdown.ShutdownCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.builtincommands.sourcecode.SourceCodeCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutCommand

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
		fun bindCoinFlipCommand(coinFlipCommand: CoinFlipCommand): Command

		@Binds
		@IntoSet
		fun bindGetConfigCommand(getConfigCommand: GetConfigCommand): Command

		@Binds
		@DevCommand
		@IntoSet
		fun bindDevTestCommand(devTestCommand: DevTestCommand): Command

		// disabled for now
		// @Binds
		// @IntoSet
		// fun bindEmojiStatsCommand(emojiStatsCommand: EmojiStatsCommand): Command

		@Binds
		@IntoSet
		fun bindMagic8BallCommand(magic8BallCommand: Magic8BallCommand): Command

		@Binds
		@IntoSet
		fun bindPollCreationCommand(pollCreationCommand: PollCreationCommand): Command

		@Binds
		@IntoSet
		fun bindPollQueryCommand(pollQueryCommand: PollQueryCommand): Command

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

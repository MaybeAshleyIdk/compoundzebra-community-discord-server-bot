package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands

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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import java.nio.file.Path

public class BuiltInCommandsFactory(
	private val configSupplier: ConfigSupplier,
	private val configFilePath: Path,
	// private val emojiStatsManager: EmojiStatsManager,
	private val pollCreator: PollCreator,
	private val pollHolder: PollHolder,
	private val selfTimeoutService: SelfTimeoutService,
	private val shutdownRequester: ShutdownRequester,
	private val botEnvironmentType: BotEnvironmentType,
	private val logger: Logger,
) {

	private fun createDevCommands(): Set<Command> {
		when (this.botEnvironmentType) {
			BotEnvironmentType.DEVELOPMENT -> Unit
			BotEnvironmentType.PRODUCTION -> return emptySet()
		}

		return setOf(
			DevTestCommand(),
		)
	}

	private fun createMainCommands(): Set<Command> {
		return setOf(
			CoinFlipCommand(this.configSupplier),
			GetConfigCommand(this.configSupplier, this.configFilePath),
			// EmojiStatsCommand(this.configSupplier, this.emojiStatsManager),
			Magic8BallCommand(this.configSupplier, this.logger),
			PollCreationCommand(this.configSupplier, this.pollCreator),
			PollQueryCommand(this.configSupplier, this.pollHolder),
			DieRollingCommand(),
			RngCommand(this.configSupplier),
			SelfTimeoutCommand(this.selfTimeoutService),
			ShutdownCommand(this.configSupplier, this.shutdownRequester),
			SourceCodeCommand(),
		)
	}

	public fun create(): Set<Command> {
		return (this.createMainCommands() + this.createDevCommands())
	}
}

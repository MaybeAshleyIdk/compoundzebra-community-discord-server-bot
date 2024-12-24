@file:Suppress("ktlint:standard:spacing-between-declarations-with-comments")

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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.nio.file.Path

public class BuiltInCommandsModule(
	scope: DiScope,
	private val configSupplier: ConfigSupplier,
	private val configFilePath: Path,
	// private val emojiStatsManager: EmojiStatsManager,
	private val pollCreator: PollCreator,
	private val pollHolder: PollHolder,
	private val selfTimeoutService: SelfTimeoutService,
	private val shutdownRequester: ShutdownRequester,
	private val botEnvironmentType: BotEnvironmentType,
	private val logger: Logger,
) : DiModule(scope) {

	private val coinFlipCommand: CoinFlipCommand
		get() {
			return CoinFlipCommand(this.configSupplier)
		}

	private val devTestCommand: DevTestCommand
		get() {
			return DevTestCommand()
		}

	private val getConfigCommand: GetConfigCommand
		get() {
			return GetConfigCommand(this.configSupplier, this.configFilePath)
		}

	// disabled for now
	// private val emojiStatsCommand: EmojiStatsCommand
	// 	get() {
	// 		return EmojiStatsCommand(this.configSupplier, this.emojiStatsManager)
	// 	}

	private val magic8BallCommand: Magic8BallCommand
		get() {
			return Magic8BallCommand(this.configSupplier, this.logger)
		}

	private val pollCreationCommand: PollCreationCommand
		get() {
			return PollCreationCommand(this.configSupplier, this.pollCreator)
		}

	private val pollQueryCommand: PollQueryCommand
		get() {
			return PollQueryCommand(this.configSupplier, this.pollHolder)
		}

	private val dieRollingCommand: DieRollingCommand
		get() {
			return DieRollingCommand()
		}

	private val rngCommand: RngCommand
		get() {
			return RngCommand(this.configSupplier)
		}

	private val selfTimeoutCommand: SelfTimeoutCommand
		get() {
			return SelfTimeoutCommand(this.selfTimeoutService)
		}

	private val shutdownCommand: ShutdownCommand
		get() {
			return ShutdownCommand(this.configSupplier, this.shutdownRequester)
		}

	private val sourceCodeCommand: SourceCodeCommand
		get() {
			return SourceCodeCommand()
		}

	private val devCommands: Set<Command>
		get() {
			return setOf(
				this.devTestCommand,
			)
		}

	public val builtInCommands: Set<Command> by this.singleton {
		val mainCommands: Set<Command> =
			setOf(
				this.coinFlipCommand,
				this.getConfigCommand,
				// this.emojiStatsCommand,
				this.magic8BallCommand,
				this.pollCreationCommand,
				this.pollQueryCommand,
				this.dieRollingCommand,
				this.rngCommand,
				this.selfTimeoutCommand,
				this.shutdownCommand,
				this.sourceCodeCommand,
			)

		val devCommands: Set<Command> =
			when (this.botEnvironmentType) {
				BotEnvironmentType.DEVELOPMENT -> this.devCommands
				BotEnvironmentType.PRODUCTION -> emptySet()
			}

		mainCommands + devCommands
	}
}

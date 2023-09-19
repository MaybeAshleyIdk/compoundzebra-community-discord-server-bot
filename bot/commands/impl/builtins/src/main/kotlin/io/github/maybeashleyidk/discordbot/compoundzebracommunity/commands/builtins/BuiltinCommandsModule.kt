package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.coinflip.di.CoinFlipCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.config.di.ConfigCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.emojistats.di.EmojiStatsCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.magic8ball.di.Magic8BallCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.polls.di.PollCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.shutdown.di.ShutdownCommandsModule

@Module(
	includes = [
		CoinFlipCommandsModule::class,
		ConfigCommandsModule::class,
		EmojiStatsCommandsModule::class,
		Magic8BallCommandsModule::class,
		PollCommandsModule::class,
		ShutdownCommandsModule::class,
	],
)
public object BuiltinCommandsModule

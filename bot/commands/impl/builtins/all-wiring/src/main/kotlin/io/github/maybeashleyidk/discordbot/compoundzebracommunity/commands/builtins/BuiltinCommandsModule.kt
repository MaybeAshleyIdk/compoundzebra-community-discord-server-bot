package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.coinflip.di.CoinFlipCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.config.di.ConfigCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.magic8ball.di.Magic8BallCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.polls.di.PollCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.rng.di.RngCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.shutdown.di.ShutdownCommandsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.test.DevCommandsModule

@Module(
	includes = [
		CoinFlipCommandsModule::class,
		ConfigCommandsModule::class,
		DevCommandsModule::class,
		Magic8BallCommandsModule::class,
		PollCommandsModule::class,
		RngCommandsModule::class,
		ShutdownCommandsModule::class,
	],
)
public object BuiltinCommandsModule

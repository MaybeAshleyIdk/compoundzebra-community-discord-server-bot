package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.coinflip.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.coinflip.CoinFlipCommand

@Module(includes = [CoinFlipCommandsModule.Bindings::class])
public object CoinFlipCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindCoinFlipCommand(coinFlipCommand: CoinFlipCommand): Command
	}
}

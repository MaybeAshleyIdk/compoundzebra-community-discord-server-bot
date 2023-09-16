package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.emojistats.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.emojistats.EmojiStatsCommand

@Module(includes = [EmojiStatsCommandsModule.Bindings::class])
public object EmojiStatsCommandsModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindEmojiStatsCommand(emojiStatsCommand: EmojiStatsCommand): Command
	}
}

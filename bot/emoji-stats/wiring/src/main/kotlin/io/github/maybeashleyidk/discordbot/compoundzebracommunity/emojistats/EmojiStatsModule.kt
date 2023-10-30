package io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats

import dagger.Binds
import dagger.Module

@Module(includes = [EmojiStatsModule.Bindings::class])
public object EmojiStatsModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindEmojiStatsManager(emojiStatsManagerImpl: EmojiStatsManagerImpl): EmojiStatsManager
	}
}

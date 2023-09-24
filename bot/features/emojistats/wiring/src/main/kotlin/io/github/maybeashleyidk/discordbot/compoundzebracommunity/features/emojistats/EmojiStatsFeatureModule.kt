package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats

import dagger.Binds
import dagger.Module

@Module(includes = [EmojiStatsFeatureModule.Bindings::class])
public object EmojiStatsFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindEmojiStatsManager(emojiStatsManagerImpl: EmojiStatsManagerImpl): EmojiStatsManager
	}
}

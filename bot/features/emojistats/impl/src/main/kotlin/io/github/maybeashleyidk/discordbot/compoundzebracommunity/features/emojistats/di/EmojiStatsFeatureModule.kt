package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.di

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.EmojiStatsManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.EmojiStatsManagerImpl

@Module(includes = [EmojiStatsFeatureModule.Bindings::class])
public object EmojiStatsFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindEmojiStatsManager(emojiStatsManagerImpl: EmojiStatsManagerImpl): EmojiStatsManager
	}
}

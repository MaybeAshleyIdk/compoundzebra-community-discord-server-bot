package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.emojistats.EmojiStatsFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollsFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.ShutdownFeatureModule

@Module(
	includes = [
		EmojiStatsFeatureModule::class,
		ShutdownFeatureModule::class,
		PollsFeatureModule::class,
	],
)
public object FeaturesModule

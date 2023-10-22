package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollsFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.ShutdownFeatureModule

@Module(
	includes = [
		ShutdownFeatureModule::class,
		PollsFeatureModule::class,
	],
)
public object FeaturesModule

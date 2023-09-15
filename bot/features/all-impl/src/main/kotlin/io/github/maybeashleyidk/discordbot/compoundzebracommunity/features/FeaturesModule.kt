package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.di.PollsFeatureModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.di.ShutdownFeatureModule

@Module(
	includes = [
		ShutdownFeatureModule::class,
		PollsFeatureModule::class,
	],
)
public object FeaturesModule

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.di.PollsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.di.ShutdownFeatureModule

@Module(
	includes = [
		ShutdownFeatureModule::class,
		PollsModule::class,
	],
)
public object FeaturesModule

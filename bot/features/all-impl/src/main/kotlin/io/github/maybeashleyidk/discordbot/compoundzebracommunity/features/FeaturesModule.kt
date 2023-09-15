package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.di.PollsModule

@Module(
	includes = [
		PollsModule::class,
	],
)
public object FeaturesModule

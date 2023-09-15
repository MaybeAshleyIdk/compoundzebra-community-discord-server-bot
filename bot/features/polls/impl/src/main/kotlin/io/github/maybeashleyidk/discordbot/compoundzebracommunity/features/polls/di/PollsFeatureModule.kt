package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollEventsListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls.PollManager
import net.dv8tion.jda.api.hooks.EventListener

@Module(includes = [PollsFeatureModule.Bindings::class])
public object PollsFeatureModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindEventListener(pollEventsListener: PollEventsListener): EventListener

		@Binds
		fun bindPollCreator(pollManager: PollManager): PollCreator

		@Binds
		fun bindPollHolder(pollManager: PollManager): PollHolder
	}
}

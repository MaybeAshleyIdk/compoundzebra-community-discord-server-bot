package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import net.dv8tion.jda.api.hooks.EventListener

@Module(includes = [PollsModule.Bindings::class])
public object PollsModule {

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

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls.PollCreator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.polls.PollHolder
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollEventsListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollManager
import net.dv8tion.jda.api.hooks.EventListener

@Module
internal interface PollsModule {

	@Binds
	@IntoSet
	fun bindPollEventsListener(pollEventsListener: PollEventsListener): EventListener

	@Binds
	fun bindPollCreator(pollManager: PollManager): PollCreator

	@Binds
	fun bindPollHolder(pollManager: PollManager): PollHolder
}

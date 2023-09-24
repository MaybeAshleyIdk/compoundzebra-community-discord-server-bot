package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import net.dv8tion.jda.api.hooks.EventListener

@Module(includes = [ConditionalMessagesModule.Bindings::class])
public object ConditionalMessagesModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindEventListener(conditionalMessageEventListener: ConditionalMessageEventListener): EventListener
	}
}

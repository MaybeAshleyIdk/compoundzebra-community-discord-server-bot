package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistenermediator

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import net.dv8tion.jda.api.hooks.EventListener

@Module(includes = [EventListenerMediatorModule.Bindings::class])
public class EventListenerMediatorModule {

	@Module
	internal interface Bindings {

		@Binds
		@IntoSet
		fun bindEventListener(eventListenerMediator: EventListenerMediator): EventListener
	}
}

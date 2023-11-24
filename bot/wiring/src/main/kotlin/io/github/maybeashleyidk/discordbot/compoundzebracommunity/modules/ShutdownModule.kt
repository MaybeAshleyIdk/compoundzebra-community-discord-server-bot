package io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbackregistry.ShutdownCallbackRegistryModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowneventhandler.ShutdownEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager.ShutdownManagerModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownrequest.ShutdownRequestModule

@Module(
	includes = [
		ShutdownManagerModule::class,
		ShutdownEventHandlingModule::class,
		ShutdownCallbackRegistryModule::class,
		ShutdownRequestModule::class,
	],
)
internal object ShutdownModule

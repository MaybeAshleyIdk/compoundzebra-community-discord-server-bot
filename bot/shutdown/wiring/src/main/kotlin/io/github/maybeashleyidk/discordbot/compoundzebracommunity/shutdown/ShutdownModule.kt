package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistryModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManagerModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequestModule

@Module(
	includes = [
		ShutdownManagerModule::class,
		ShutdownEventHandlingModule::class,
		ShutdownCallbackRegistryModule::class,
		ShutdownRequestModule::class,
	],
)
public object ShutdownModule

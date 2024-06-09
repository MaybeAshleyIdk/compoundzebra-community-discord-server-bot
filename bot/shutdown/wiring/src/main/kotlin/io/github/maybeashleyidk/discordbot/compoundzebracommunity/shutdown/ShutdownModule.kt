package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistrationModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManagementModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequestingModule

@Module(
	includes = [
		ShutdownManagementModule::class,
		ShutdownEventHandlingModule::class,
		ShutdownCallbackRegistrationModule::class,
		ShutdownRequestingModule::class,
	],
)
public object ShutdownModule

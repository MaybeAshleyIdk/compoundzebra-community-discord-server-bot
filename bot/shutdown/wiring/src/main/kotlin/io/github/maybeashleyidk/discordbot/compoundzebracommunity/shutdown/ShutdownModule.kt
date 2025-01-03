package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownManagerCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownManagerEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManagerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownManagerRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import java.util.function.Supplier
import net.dv8tion.jda.api.JDA as Jda

public class ShutdownModule(
	scope: DiScope,
	private val jdaSupplier: Supplier<Jda>,
	private val logger: Logger,
) : DiModule(scope) {

	private val shutdownManager: ShutdownManager by this.singleton {
		ShutdownManagerImpl(this.jdaSupplier, this.logger)
	}

	public val shutdownEventHandler: ShutdownEventHandler
		get() {
			return ShutdownManagerEventHandler(this.shutdownManager)
		}

	public val shutdownCallbackRegistry: ShutdownCallbackRegistry
		get() {
			return ShutdownManagerCallbackRegistry(this.shutdownManager)
		}

	public val shutdownRequester: ShutdownRequester
		get() {
			return ShutdownManagerRequester(this.shutdownManager)
		}
}

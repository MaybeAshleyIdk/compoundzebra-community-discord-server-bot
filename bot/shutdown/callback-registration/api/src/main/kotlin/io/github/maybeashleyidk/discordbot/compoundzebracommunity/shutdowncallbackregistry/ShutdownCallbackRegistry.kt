package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbackregistry

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnBeforeShutdownCallback
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Object that allows registering callbacks either before a shutdown has been initiated or after the associated JDA has
 * full shut down.
 *
 * **Regardless of when a callback is registered, the following behavior is guaranteed:**
 *
 * * Callbacks may be executed on any thread
 *
 * **For all callbacks that are registered *before* a shutdown has been requested or initiated,
 * the following behavior is guaranteed:**
 *
 * * All [OnBeforeShutdownCallback]s are executed before a shutdown has been initiated
 *   (but not before a shutdown has been requested)
 * * All [OnBeforeShutdownCallback]s are executed in the same order as they were registered
 * * All [OnBeforeShutdownCallback]s are executed before any [OnAfterShutdownCallback]s
 * * All [OnAfterShutdownCallback]s are executed in the same order as they were registered
 *
 * **For callbacks that are registered *after* a shutdown has been requested or has been initiated,
 * the following behavior is guaranteed:**
 *
 * * Any callbacks that are registered are executed immediately (no order can be guaranteed)
 */
public interface ShutdownCallbackRegistry {

	public fun registerCallback(callback: OnBeforeShutdownCallback)

	public fun registerCallback(callback: OnAfterShutdownCallback)

	public fun unregisterCallback(callback: OnAfterShutdownCallback)

	public fun unregisterCallback(callback: OnBeforeShutdownCallback)
}

/**
 * Suspends the current coroutine until *after* the associated JDA instance has fully shut down.
 */
public suspend fun ShutdownCallbackRegistry.awaitShutdown() {
	suspendCancellableCoroutine { continuation: CancellableContinuation<Unit> ->
		val callback =
			OnAfterShutdownCallback {
				continuation.resume(Unit)
			}

		this.registerCallback(callback)

		continuation
			.invokeOnCancellation {
				this.unregisterCallback(callback)
			}
	}
}

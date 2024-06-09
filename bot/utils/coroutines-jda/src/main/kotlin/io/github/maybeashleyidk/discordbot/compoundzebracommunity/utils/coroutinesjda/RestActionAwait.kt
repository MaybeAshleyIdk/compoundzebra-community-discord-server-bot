package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.future.await
import kotlinx.coroutines.suspendCancellableCoroutine
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.utils.concurrent.Task
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

public suspend fun <T> RestAction<T>.await(): T {
	val future: CompletableFuture<T> = this.submit()
	return future.await()
}

public suspend fun <T> Task<T>.await(): T {
	return suspendCancellableCoroutine { continuation: CancellableContinuation<T> ->
		continuation.invokeOnCancellation {
			this.cancel()
		}

		this
			.onSuccess(continuation::resume)
			.onError(continuation::resumeWithException)
	}
}

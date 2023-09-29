package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda

import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.requests.RestAction
import java.util.concurrent.CompletableFuture

public suspend fun <T> RestAction<T>.await(): T {
	val future: CompletableFuture<T> = this.submit()
	return future.await()
}

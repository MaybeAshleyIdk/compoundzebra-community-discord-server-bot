package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReadWriteMutexAtomicVarTest {

	// TODO
}

private fun runBlockingWithCachedThreadPool(block: suspend CoroutineScope.() -> Unit) {
	Executors.newCachedThreadPool()
		.use { executorService: ExecutorService ->
			val dispatcher: CoroutineDispatcher = executorService.asCoroutineDispatcher()
			runBlocking(dispatcher, block)
		}
}

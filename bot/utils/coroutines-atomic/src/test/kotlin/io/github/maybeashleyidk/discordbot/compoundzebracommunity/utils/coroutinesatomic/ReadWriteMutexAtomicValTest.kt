package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.internal.ReadWriteMutexAtomicVal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.milliseconds

class ReadWriteMutexAtomicValTest {

	@Test
	fun `only one visitor can be active at the same time`() {
		val atomic: AtomicVal<Unit> = ReadWriteMutexAtomicVal(Unit)
		val currentVisitor = AtomicInteger(-1)

		suspend fun visit(n: Int, block: suspend () -> Unit) {
			atomic.visit {
				val b1: Boolean = currentVisitor.compareAndSet(-1, n)
				assertTrue(b1)

				block()

				val b2: Boolean = currentVisitor.compareAndSet(n, -1)
				assertTrue(b2)
			}
		}

		runBlockingWithCachedThreadPool {
			this@runBlockingWithCachedThreadPool.launch {
				visit(0) {
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(100.milliseconds)
				visit(1) {
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(300.milliseconds)
				visit(3) {
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(200.milliseconds)
				visit(2) {
					delay(400.milliseconds)
				}
			}
		}

		assertEquals(-1, currentVisitor.get())
	}

	@Test
	fun `visitors are fair`() {
		val atomicList: AtomicVal<MutableList<Int>> = ReadWriteMutexAtomicVal(ArrayList())

		runBlockingWithCachedThreadPool {
			this@runBlockingWithCachedThreadPool.launch {
				atomicList.visit { list: MutableList<Int> ->
					list.add(0)
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(100.milliseconds)
				atomicList.visit { list: MutableList<Int> ->
					list.add(1)
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(300.milliseconds)
				atomicList.visit { list: MutableList<Int> ->
					list.add(3)
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(200.milliseconds)
				atomicList.visit { list: MutableList<Int> ->
					list.add(2)
					delay(400.milliseconds)
				}
			}
		}

		runBlocking {
			atomicList.visit { list: List<Int> ->
				assertEquals(listOf(0, 1, 2, 3), list)
			}
		}
	}
}

private fun runBlockingWithCachedThreadPool(block: suspend CoroutineScope.() -> Unit) {
	Executors.newCachedThreadPool()
		.use { executorService: ExecutorService ->
			val dispatcher: CoroutineDispatcher = executorService.asCoroutineDispatcher()
			runBlocking(dispatcher, block)
		}
}

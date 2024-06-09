package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Vector
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.milliseconds

class SemaphoreReadWriteMutexTest {

	@Test
	fun `multiple read locks can be held at the same time`() {
		val mutex = SemaphoreReadWriteMutex()
		var success = false

		runBlockingWithCachedThreadPool {
			mutex.withReadLock {
				mutex.withReadLock {
					mutex.withReadLock {
						success = true
					}
				}
			}
		}

		assertTrue(success)
	}

	@Test
	fun `only one write lock can be held at the same time`() {
		val mutex = SemaphoreReadWriteMutex()
		val currentLock = AtomicInteger(-1)

		suspend fun withWriteLock(n: Int, block: suspend () -> Unit) {
			mutex.withWriteLock {
				val b1: Boolean = currentLock.compareAndSet(-1, n)
				assertTrue(b1)

				block()

				val b2: Boolean = currentLock.compareAndSet(n, -1)
				assertTrue(b2)
			}
		}

		runBlockingWithCachedThreadPool {
			this@runBlockingWithCachedThreadPool.launch {
				withWriteLock(0) {
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(100.milliseconds)
				withWriteLock(1) {
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(300.milliseconds)
				withWriteLock(3) {
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(200.milliseconds)
				withWriteLock(2) {
					delay(400.milliseconds)
				}
			}
		}

		assertEquals(-1, currentLock.get())
	}

	@Test
	fun `a write lock waits until all read locks are finished`() {
		val mutex = SemaphoreReadWriteMutex()
		val finishedReadLocks = AtomicInteger(0)
		var success = false

		runBlockingWithCachedThreadPool {
			this@runBlockingWithCachedThreadPool.launch {
				mutex.withReadLock {
					delay(250.milliseconds)
					finishedReadLocks.incrementAndGet()
				}
			}
			this@runBlockingWithCachedThreadPool.launch {
				delay(100.milliseconds)
				mutex.withReadLock {
					delay(250.milliseconds)
					finishedReadLocks.incrementAndGet()
				}
			}
			this@runBlockingWithCachedThreadPool.launch {
				delay(300.milliseconds)
				mutex.withReadLock {
					delay(250.milliseconds)
					finishedReadLocks.incrementAndGet()
				}
			}
			this@runBlockingWithCachedThreadPool.launch {
				delay(200.milliseconds)
				mutex.withReadLock {
					delay(250.milliseconds)
					finishedReadLocks.incrementAndGet()
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(275.milliseconds)
				mutex.withWriteLock {
					success = (finishedReadLocks.get() == 4)
				}
			}
		}

		assertTrue(success)
	}

	@Test
	fun `all read locks wait until the write lock is finished`() {
		val mutex = SemaphoreReadWriteMutex()
		val writeFinished = AtomicBoolean(false)
		val readsSuccessful: MutableList<Boolean> = Vector(4)

		suspend fun testInReadLock() {
			mutex.withReadLock {
				readsSuccessful.add(writeFinished.get())
			}
		}

		runBlockingWithCachedThreadPool {
			this@runBlockingWithCachedThreadPool.launch {
				mutex.withWriteLock {
					delay(400.milliseconds)
					writeFinished.set(true)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(100.milliseconds)
				testInReadLock()
			}
			this@runBlockingWithCachedThreadPool.launch {
				delay(300.milliseconds)
				testInReadLock()
			}
			this@runBlockingWithCachedThreadPool.launch {
				delay(500.milliseconds)
				testInReadLock()
			}
			this@runBlockingWithCachedThreadPool.launch {
				delay(200.milliseconds)
				testInReadLock()
			}
		}

		assertTrue(writeFinished.get())
		assertEquals(listOf(true, true, true, true), readsSuccessful)
	}

	@Test
	fun `write locks are fair`() {
		val mutex = SemaphoreReadWriteMutex()
		val order: MutableList<Int> = Vector(4)

		runBlockingWithCachedThreadPool {
			this@runBlockingWithCachedThreadPool.launch {
				mutex.withWriteLock {
					order.add(0)
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(100.milliseconds)
				mutex.withWriteLock {
					order.add(1)
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(300.milliseconds)
				mutex.withWriteLock {
					order.add(3)
					delay(400.milliseconds)
				}
			}

			this@runBlockingWithCachedThreadPool.launch {
				delay(200.milliseconds)
				mutex.withWriteLock {
					order.add(2)
					delay(400.milliseconds)
				}
			}
		}

		assertEquals(listOf(0, 1, 2, 3), order)
	}
}

private fun runBlockingWithCachedThreadPool(block: suspend CoroutineScope.() -> Unit) {
	Executors.newCachedThreadPool()
		.use { executorService: ExecutorService ->
			val dispatcher: CoroutineDispatcher = executorService.asCoroutineDispatcher()
			runBlocking(dispatcher, block)
		}
}

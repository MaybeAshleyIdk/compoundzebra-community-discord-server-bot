package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines.ReadWriteMutex
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock

// yes, this is more of an "ReadWriteSemaphore", but this was an easy way to implement something like this
internal class ReadWriteMutexImpl : ReadWriteMutex {

	private val semaphore: Semaphore = Semaphore(permits = 1)

	private val readCountMutex: Mutex = Mutex()
	private var readCount: UInt = 0u

	override suspend fun lockForReading() {
		this.readCountMutex.withLock {
			if (this.readCount == 0u) {
				this.semaphore.acquire()
			}

			++(this.readCount)
		}
	}

	override suspend fun unlockForReading() {
		this.readCountMutex.withLock {
			check(this.readCount > 0u)

			--(this.readCount)

			if (this.readCount == 0u) {
				this.semaphore.release()
			}
		}
	}

	override suspend fun lockForWriting() {
		this.semaphore.acquire()
	}

	override suspend fun unlockForWriting() {
		this.semaphore.release()
	}
}

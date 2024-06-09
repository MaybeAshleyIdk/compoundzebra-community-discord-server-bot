package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock

internal class SemaphoreReadWriteMutex : ReadWriteMutex {

	private sealed class State {

		data object Idling : State()

		data class Reading(val count: UInt) : State() {

			init {
				require(this.count > 0u)
			}

			fun plusOne(): Reading {
				return this.copy(count = (this.count + 1u))
			}

			fun minusOne(): Reading? {
				if (this.count == 1u) {
					return null
				}

				return this.copy(count = (this.count - 1u))
			}

			companion object {

				val ONE: Reading = Reading(count = 1u)
			}
		}

		data object Writing : State()
	}

	private val syncMutex: Mutex = Mutex()
	private val rwSemaphore: Semaphore = Semaphore(permits = 1)
	private var currentState: State = State.Idling

	override suspend fun lockForReading() {
		val reading: Boolean = this.tryLockForReading()
		if (reading) {
			return
		}

		this.rwSemaphore.acquire()
		this.syncMutex.withLock {
			this.currentState = when (this.currentState) {
				is State.Idling -> State.Reading.ONE
				is State.Reading -> error("Unexpected state: reading")
				is State.Writing -> error("Unexpected state: writing")
			}
		}
	}

	private suspend fun tryLockForReading(): Boolean {
		return this.syncMutex
			.withLock {
				this.tryLockForReadingUnsynced()
			}
	}

	private fun tryLockForReadingUnsynced(): Boolean {
		val acquired: Boolean = this.rwSemaphore.tryAcquire()

		val newReadingState: State.Reading? =
			when (val currentState: State = this.currentState) {
				is State.Idling -> {
					check(acquired) {
						"Expected the read-write semaphore to be unlocked"
					}

					State.Reading.ONE
				}

				is State.Reading -> {
					if (acquired) {
						this.rwSemaphore.release()
						error("Expected the read-write semaphore to be locked")
					}

					currentState.plusOne()
				}

				is State.Writing -> {
					if (acquired) {
						this.rwSemaphore.release()
						error("Expected the read-write semaphore to be locked")
					}

					null
				}
			}

		if (newReadingState == null) {
			return false
		}

		this.currentState = newReadingState
		return true
	}

	override suspend fun unlockForReading() {
		this.syncMutex.withLock {
			val newState: State =
				when (val currentState: State = this.currentState) {
					is State.Idling -> error("Unexpected state: idling")
					is State.Reading -> (currentState.minusOne() ?: State.Idling)
					is State.Writing -> error("Unexpected state: writing")
				}

			this.currentState = newState

			if (newState is State.Idling) {
				this.rwSemaphore.release()
			}
		}
	}

	override suspend fun lockForWriting() {
		val writing: Boolean = this.tryLockForWriting()
		if (writing) {
			return
		}

		this.rwSemaphore.acquire()
		this.syncMutex.withLock {
			this.currentState = when (this.currentState) {
				is State.Idling -> State.Writing
				is State.Reading -> error("Unexpected state: reading")
				is State.Writing -> error("Unexpected state: writing")
			}
		}
	}

	private suspend fun tryLockForWriting(): Boolean {
		return this.syncMutex
			.withLock {
				this.tryLockForWritingUnsynced()
			}
	}

	private fun tryLockForWritingUnsynced(): Boolean {
		val acquired: Boolean = this.rwSemaphore.tryAcquire()

		val newWritingState: State.Writing? =
			when (this.currentState) {
				is State.Idling -> {
					check(acquired) {
						"Expected the read-write semaphore to be unlocked"
					}

					State.Writing
				}

				is State.Reading -> {
					if (acquired) {
						this.rwSemaphore.release()
						error("Expected the read-write semaphore to be locked")
					}

					null
				}

				is State.Writing -> {
					if (acquired) {
						this.rwSemaphore.release()
						error("Expected the read-write semaphore to be locked")
					}

					null
				}
			}

		if (newWritingState == null) {
			return false
		}

		this.currentState = newWritingState
		return true
	}

	override suspend fun unlockForWriting() {
		this.syncMutex.withLock {
			val newState: State.Idling =
				when (this.currentState) {
					is State.Idling -> error("Unexpected state: idling")
					is State.Reading -> error("Unexpected state: reading")
					is State.Writing -> State.Idling
				}

			this.currentState = newState
			this.rwSemaphore.release()
		}
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class ImmutableValueImpl<T>(initialValue: T) : ImmutableMutexValue<T> {

	private inner class RefImpl : ImmutableMutexValue.Ref<T> {

		override suspend fun get(): T {
			return this@ImmutableValueImpl.value
		}

		override suspend fun set(newValue: T) {
			this@ImmutableValueImpl.value = newValue
		}
	}

	private val mutex: Mutex = Mutex()
	private var value: T = initialValue

	override suspend fun get(): T {
		return this.mutex
			.withLock {
				this.value
			}
	}

	override suspend fun <R> visit(block: suspend (ImmutableMutexValue.Ref<T>) -> R): R {
		return this.mutex
			.withLock {
				block(this.RefImpl())
			}
	}

	override suspend fun getAndSet(newValue: T): T {
		return this.mutex
			.withLock {
				val oldValue: T = this.value

				this.value = newValue

				oldValue
			}
	}
}

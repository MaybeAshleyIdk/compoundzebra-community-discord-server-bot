package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVar
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVar.Handle
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.ReadWriteMutex
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.withReadLock
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.withWriteLock

internal class ReadWriteMutexAtomicVar<T>(initialValue: T) : AtomicVar<T> {

	private val mutex: ReadWriteMutex = ReadWriteMutex()
	private var value: T = initialValue

	override suspend fun get(): T {
		return this.mutex.withReadLock(this::value)
	}

	override suspend fun getAndSet(newValue: T): T {
		return this.mutex
			.withWriteLock {
				val oldValue: T = this.value
				this.value = newValue
				oldValue
			}
	}

	override suspend fun <R> visit(visitor: suspend (Handle<T>) -> R): R {
		return this.mutex
			.withWriteLock {
				visitor(this.HandleImpl())
			}
	}

	private inner class HandleImpl : Handle<T> {

		override suspend fun get(): T {
			return this@ReadWriteMutexAtomicVar.value
		}

		override suspend fun set(newValue: T) {
			this@ReadWriteMutexAtomicVar.value = newValue
		}
	}
}

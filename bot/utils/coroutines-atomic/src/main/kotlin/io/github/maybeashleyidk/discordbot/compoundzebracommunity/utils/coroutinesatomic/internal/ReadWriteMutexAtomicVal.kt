package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVal
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.ReadWriteMutex
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.withReadLock
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.withWriteLock

internal class ReadWriteMutexAtomicVal<T>(private val value: T) : AtomicVal<T> {

	private val mutex: ReadWriteMutex = ReadWriteMutex()

	override suspend fun get(): T {
		return this.mutex.withReadLock(this::value)
	}

	override suspend fun <R> visit(visitor: suspend (value: T) -> R): R {
		return this.mutex
			.withWriteLock {
				visitor(this.value)
			}
	}
}

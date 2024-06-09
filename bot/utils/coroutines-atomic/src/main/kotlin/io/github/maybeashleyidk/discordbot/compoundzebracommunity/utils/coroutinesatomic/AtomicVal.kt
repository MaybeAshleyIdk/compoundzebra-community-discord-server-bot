package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.internal.ReadWriteMutexAtomicVal

public interface AtomicVal<T> {

	public suspend fun get(): T

	public suspend fun <R> visit(visitor: suspend (value: T) -> R): R
}

public fun <T> AtomicVal(value: T): AtomicVal<T> {
	return ReadWriteMutexAtomicVal(value)
}

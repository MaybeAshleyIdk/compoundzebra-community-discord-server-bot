package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.internal.ReadWriteMutexAtomicVar

public interface AtomicVar<T> {

	public interface Handle<T> {

		public suspend fun get(): T

		public suspend fun set(newValue: T)
	}

	public suspend fun get(): T

	public suspend fun getAndSet(newValue: T): T

	public suspend fun <R> visit(visitor: suspend (Handle<T>) -> R): R
}

public fun <T> AtomicVar(initialValue: T): AtomicVar<T> {
	return ReadWriteMutexAtomicVar(initialValue)
}

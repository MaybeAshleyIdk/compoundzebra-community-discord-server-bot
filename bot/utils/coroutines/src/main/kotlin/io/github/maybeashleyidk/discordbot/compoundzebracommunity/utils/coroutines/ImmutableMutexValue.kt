package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines

import kotlinx.coroutines.sync.Mutex

/**
 * Protects an immutable value of type [T] via a [Mutex].
 */
public interface ImmutableMutexValue<T> {

	public interface Ref<T> {

		public suspend fun get(): T

		public suspend fun set(newValue: T)
	}

	public suspend fun get(): T

	public suspend fun <R> visit(block: suspend (Ref<T>) -> R): R

	public suspend fun getAndSet(newValue: T): T
}

// <https://github.com/pinterest/ktlint/issues/2350>
@Suppress("ktlint:standard:function-naming")
public fun <T> ImmutableMutexValue(initialValue: T): ImmutableMutexValue<T> {
	return ImmutableValueImpl(initialValue)
}

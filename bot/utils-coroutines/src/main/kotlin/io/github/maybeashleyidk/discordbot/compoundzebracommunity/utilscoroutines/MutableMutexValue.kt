package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines

import kotlinx.coroutines.sync.Mutex

/**
 * Protects a mutable value of type [T] via a [Mutex].
 */
public interface MutableMutexValue<T> {

	public suspend fun <R> visit(block: suspend (T) -> R): R
}

// <https://github.com/pinterest/ktlint/issues/2350>
@Suppress("ktlint:standard:function-naming")
public fun <T> MutableMutexValue(initialValue: T): MutableMutexValue<T> {
	return MutableMutexValueImpl(initialValue)
}

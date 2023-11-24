package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class MutableMutexValueImpl<T>(initialValue: T) : MutableMutexValue<T> {

	private val mutex: Mutex = Mutex()
	private var value: T = initialValue

	@Suppress("AddOperatorModifier")
	override suspend fun <R> visit(block: suspend (T) -> R): R {
		return this.mutex
			.withLock {
				block(this.value)
			}
	}
}

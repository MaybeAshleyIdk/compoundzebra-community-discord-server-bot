package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope

import java.util.Collections

public fun DiScope(): DiScope {
	return DiScopeImpl()
}

private class DiScopeImpl : DiScope {

	private val lazies: MutableMap<() -> Any, Lazy<Any>> = Collections.synchronizedMap(HashMap())

	override fun <T : Any> save(key: Any, initializer: () -> T): Lazy<T> {
		@Suppress("UNCHECKED_CAST")
		return this.lazies.computeIfAbsent(initializer, ::lazy) as Lazy<T>
	}
}

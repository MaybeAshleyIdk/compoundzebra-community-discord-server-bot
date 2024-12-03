package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope

public abstract class DiModule(private val scope: DiScope) {

	protected fun <T : Any> singleton(initializer: () -> T): Lazy<T> {
		return this.scope.save(key = (Identity(this) to initializer), initializer)
	}

	protected fun <T : Any> reusable(initializer: () -> T): Lazy<T> {
		return lazy(LazyThreadSafetyMode.PUBLICATION, initializer)
	}
}

private class Identity(private val obj: Any) {

	override fun equals(other: Any?): Boolean {
		return (this === other) ||
			((other is Identity) && (this.obj === other.obj))
	}

	override fun hashCode(): Int {
		return System.identityHashCode(this.obj)
	}
}

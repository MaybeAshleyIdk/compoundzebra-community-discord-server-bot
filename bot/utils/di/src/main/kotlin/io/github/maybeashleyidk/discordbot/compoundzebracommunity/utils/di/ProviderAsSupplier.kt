package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di

import java.util.function.Supplier

public fun <T : Any> Provider<T>.asSupplier(): Supplier<T> {
	return ProviderAsSupplier(provider = this)
}

private class ProviderAsSupplier<T : Any>(private val provider: Provider<T>) : Supplier<T> {

	override fun get(): T {
		return this.provider.get()
	}
}

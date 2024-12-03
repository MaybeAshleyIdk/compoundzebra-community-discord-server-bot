package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di

import kotlin.reflect.KProperty

public operator fun <T : Any> Provider<T>.getValue(
	@Suppress("unused") thisRef: Any?,
	@Suppress("unused") property: KProperty<*>,
): T {
	return this.get()
}

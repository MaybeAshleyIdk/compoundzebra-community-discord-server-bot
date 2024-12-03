package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di

import kotlin.reflect.KProperty

public typealias Provider<T> = javax.inject.Provider<T>

public operator fun <T : Any> Provider<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
	return this.get()
}

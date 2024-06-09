package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.utils

import org.gradle.api.provider.Provider
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

/**
 * Workaround for [Gradle issue #12388](https://github.com/gradle/gradle/issues/12388).
 */
internal inline fun <T : Any, S : Any> Provider<T>.mapKt(crossinline transformer: (T) -> S?): Provider<S> {
	return this
		.map { value: T ->
			val transformedValue: S? = transformer(value)
			Optional.ofNullable(transformedValue)
		}
		.filter(Optional<S>::isPresent)
		.map(Optional<S>::get)
}

internal inline fun <T : Any> Provider<T>.orElseError(crossinline lazyMessage: () -> String): Provider<T> {
	return this
		.map { value: T ->
			Optional.ofNullable(value)
		}
		.orElse(Optional.empty())
		.map { optional: Optional<T> ->
			checkNotNull(optional.getOrNull(), lazyMessage)
		}
}

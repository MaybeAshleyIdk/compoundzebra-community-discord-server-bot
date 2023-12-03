package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal

/**
 * A [Lazy]-like class that doesn't try to initialize the value again after the initializer throws an exception.
 * (like how the function [lazy] does it)
 *
 * The thread safety mode is similar to [LazyThreadSafetyMode.SYNCHRONIZED].
 */
internal class NoRetryLazy<out T>(initializer: () -> T) {

	private sealed class State<out T> {

		data class Uninitialized<out T>(
			val initializer: () -> T,
		) : State<T>()

		data class Failed(
			val error: Throwable,
		) : State<Nothing>()

		data class Succeeded<out T>(
			val value: T,
		) : State<T>()
	}

	@Volatile
	private var currentState: State<T> = State.Uninitialized(initializer)

	fun getValue(): T {
		when (val state: State<T> = this.currentState) {
			is State.Uninitialized -> Unit
			is State.Failed -> throw state.error
			is State.Succeeded -> return state.value
		}

		return synchronized(this) {
			when (val state: State<T> = this.currentState) {
				is State.Uninitialized -> this.initializeUnsynchronized(state.initializer)
				is State.Failed -> throw state.error
				is State.Succeeded -> state.value
			}
		}
	}

	fun isInitializedSuccessfully(): Boolean {
		return when (this.currentState) {
			is State.Uninitialized,
			is State.Failed,
			-> false

			is State.Succeeded -> true
		}
	}

	private inline fun initializeUnsynchronized(initializer: () -> T): T {
		val value: T =
			try {
				initializer()
			} catch (e: Throwable) {
				this.currentState = State.Failed(e)
				throw e
			}

		this.currentState = State.Succeeded(value)

		return value
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.sequences.internal

private sealed class State<out T> {

	data class Unknown<out T>(val upstream: Iterator<T>) : State<T>()

	data class Next1Cached<out T>(
		val upstream: Iterator<T>,
		val value: T,
	) : State<T>()
}

private sealed class KnownState<out T> : State<T>() {

	data class Next2Cached<out T>(
		val upstream: Iterator<T>,
		val value1: T,
		val value2: T,
	) : KnownState<T>()

	class End<out T> : KnownState<T>()
}

internal class DropLastIterator<out T>(upstream: Iterator<T>) : Iterator<T> {

	private var currentState: State<T> = State.Unknown(upstream)

	override fun hasNext(): Boolean {
		return when (val nextKnownState: KnownState<T> = this.nextKnownState()) {
			is KnownState.Next2Cached -> {
				this.currentState = nextKnownState
				true
			}

			is KnownState.End -> false
		}
	}

	override fun next(): T {
		when (val nextKnownState: KnownState<T> = this.nextKnownState()) {
			is KnownState.Next2Cached -> {
				val value: T = nextKnownState.value1
				this.currentState = State.Next1Cached(nextKnownState.upstream, nextKnownState.value2)
				return value
			}

			is KnownState.End -> throw NoSuchElementException()
		}
	}

	private fun nextKnownState(): KnownState<T> {
		val (upstream: Iterator<T>, currentValue1: T?) =
			when (val currentState: State<T> = this.currentState) {
				is State.Unknown -> (currentState.upstream to null)
				is State.Next1Cached -> (currentState.upstream to currentState.value)
				is KnownState.Next2Cached -> return currentState
				is KnownState.End -> return currentState
			}

		while (true) {
			val value1: T =
				if (currentValue1 != null) {
					currentValue1
				} else {
					if (!(upstream.hasNext())) {
						return KnownState.End()
					}

					upstream.next()
				}

			if (!(upstream.hasNext())) {
				return KnownState.End()
			}

			val value2: T = upstream.next()

			return KnownState.Next2Cached(upstream, value1, value2)
		}
	}
}

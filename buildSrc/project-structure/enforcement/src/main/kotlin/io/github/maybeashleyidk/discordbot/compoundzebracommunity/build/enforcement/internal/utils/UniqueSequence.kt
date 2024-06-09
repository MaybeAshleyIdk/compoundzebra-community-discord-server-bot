package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal.utils

internal fun <T> Sequence<T>.unique(): Sequence<T> {
	return UniqueSequence(upstream = this)
}

private class UniqueSequence<T>(private val upstream: Sequence<T>) : Sequence<T> {

	override fun iterator(): Iterator<T> {
		return UniqueIterator(this.upstream.iterator())
	}
}

private class UniqueIterator<T>(upstream: Iterator<T>) : Iterator<T> {

	private sealed class State<T> {

		data class Unknown<T>(
			val encounteredValues: MutableSet<Any?>,
			val upstream: Iterator<T>,
		) : State<T>()
	}

	private sealed class KnownState<T> : State<T>() {

		data class NextCached<T>(
			val encounteredValues: MutableSet<Any?>,
			val upstream: Iterator<T>,
			val element: T,
		) : KnownState<T>()

		class End<T> : KnownState<T>()
	}

	private var currentState: State<T> = State
		.Unknown(
			encounteredValues = mutableSetOf(),
			upstream,
		)

	override fun hasNext(): Boolean {
		return when (this.nextKnownState()) {
			is KnownState.NextCached -> true
			is KnownState.End -> false
		}
	}

	override fun next(): T {
		when (val currentState: KnownState<T> = this.nextKnownState()) {
			is KnownState.NextCached -> {
				this.currentState = State
					.Unknown(
						currentState.encounteredValues,
						currentState.upstream,
					)

				return currentState.element
			}

			is KnownState.End -> throw NoSuchElementException()
		}
	}

	private fun nextKnownState(): KnownState<T> {
		val unknownState: State.Unknown<T> =
			when (val currentState: State<T> = this.currentState) {
				is State.Unknown -> currentState
				is KnownState.End -> return currentState
				is KnownState.NextCached -> return currentState
			}

		while (true) {
			if (!(unknownState.upstream.hasNext())) {
				val nextState: KnownState.End<T> = KnownState.End()
				this.currentState = nextState
				return nextState
			}

			val nextElement: T = unknownState.upstream.next()

			if (nextElement in unknownState.encounteredValues) {
				continue
			}

			unknownState.encounteredValues.add(nextElement)

			val nextState: KnownState.NextCached<T> = KnownState
				.NextCached(
					unknownState.encounteredValues,
					unknownState.upstream,
					element = nextElement,
				)
			this.currentState = nextState
			return nextState
		}
	}
}

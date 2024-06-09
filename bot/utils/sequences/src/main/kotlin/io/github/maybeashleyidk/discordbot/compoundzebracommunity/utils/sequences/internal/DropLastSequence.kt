package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.sequences.internal

internal class DropLastSequence<out T>(private val upstream: Sequence<T>) : Sequence<T> {

	override fun iterator(): Iterator<T> {
		return DropLastIterator(this.upstream.iterator())
	}
}

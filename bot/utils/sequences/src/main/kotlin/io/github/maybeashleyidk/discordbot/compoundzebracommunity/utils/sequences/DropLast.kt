package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.sequences

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.sequences.internal.DropLastSequence

public fun <T> Sequence<T>.dropLast(): Sequence<T> {
	return DropLastSequence(upstream = this)
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.recentmessages.utils

internal fun Long.coerceToInt(): Int {
	return this
		.coerceIn((Int.MIN_VALUE.toLong())..(Int.MAX_VALUE.toLong()))
		.toInt()
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.emojistats

internal fun <K> MutableMap<K, Long>.incrementKey(key: K) {
	this
		.merge(key, 1) { old: Long, _: Long ->
			old + 1
		}
}

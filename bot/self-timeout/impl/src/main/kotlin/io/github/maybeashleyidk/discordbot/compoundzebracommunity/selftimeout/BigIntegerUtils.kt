package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import java.math.BigInteger

internal fun BigInteger.longValueClamped(): Long {
	if (this >= BigInteger.valueOf(Long.MAX_VALUE)) {
		return Long.MAX_VALUE
	}

	if (this <= BigInteger.valueOf(Long.MIN_VALUE)) {
		return Long.MIN_VALUE
	}

	return this.longValueExact()
}

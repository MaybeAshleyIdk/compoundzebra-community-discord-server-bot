package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.time.Duration

internal fun ClosedRange<Duration>.random(): Duration {
	require(!(this.isEmpty())) {
		"Cannot get random in empty range: $this"
	}

	val randomSeconds: Long = (this.start.seconds..this.endInclusive.seconds).random()

	val minNano: Int =
		if (randomSeconds == this.start.seconds) {
			this.start.nano
		} else {
			0
		}

	val maxNano: Int =
		if (randomSeconds == this.endInclusive.seconds) {
			this.endInclusive.nano
		} else {
			999_999_999
		}

	val randomNano: Int = (minNano..maxNano).random()

	return Duration.ofSeconds(randomSeconds, randomNano.toLong())
}

internal operator fun Duration.times(multiplicand: BigDecimal): Duration {
	require(!(this.isNegative)) {
		"Duration must not be negative"
	}

	val multipliedNano: BigDecimal = (this.nano.toBigDecimal() * multiplicand)

	val multipliedSeconds: BigDecimal = (this.seconds.toBigDecimal() * multiplicand)
	val multipliedSecondsIntPart: BigInteger = multipliedSeconds
		.round(MathContext(1, RoundingMode.DOWN))
		.toBigIntegerExact()
	val multipliedSecondsFracPart: BigDecimal = (multipliedSeconds - multipliedSecondsIntPart.toBigDecimal())

	val totalNano: BigInteger = (multipliedNano + (multipliedSecondsFracPart * 1_000_000_000.toBigDecimal()))
		.round(MathContext(9, RoundingMode.HALF_UP))
		.toBigIntegerExact()

	val finalNano: Int = (totalNano % 1_000_000_000.toBigInteger())
		.intValueExact()
	val finalSeconds: Long = (multipliedSecondsIntPart + (totalNano / 1_000_000_000.toBigInteger()))
		.longValueClamped()

	return Duration.ofSeconds(finalSeconds, finalNano.toLong())
}

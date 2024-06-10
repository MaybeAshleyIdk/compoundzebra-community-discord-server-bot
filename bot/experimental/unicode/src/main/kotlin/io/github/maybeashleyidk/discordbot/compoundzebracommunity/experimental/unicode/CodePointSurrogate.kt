package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

private val HIGH_SURROGATE_RANGE: ClosedRange<CodePoint> = ((U + 0xD800)..(U + 0xDBFF))
private val LOW_SURROGATE_RANGE: ClosedRange<CodePoint> = ((U + 0xDC00)..(U + 0xDFFF))

public fun CodePoint.isSurrogate(): Boolean {
	return (this in HIGH_SURROGATE_RANGE) || (this in LOW_SURROGATE_RANGE)
}

public fun CodePoint.isNotSurrogate(): Boolean {
	return !(this.isSurrogate())
}

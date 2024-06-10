package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

import kotlin.streams.asSequence

public fun String.scalarValues(): Sequence<ScalarValue> {
	return this.codePoints()
		.asSequence()
		.map(CodePoint::ofInt)
		.map { codePoint: CodePoint ->
			ScalarValue.ofCodePoint(codePoint)!!
		}
}

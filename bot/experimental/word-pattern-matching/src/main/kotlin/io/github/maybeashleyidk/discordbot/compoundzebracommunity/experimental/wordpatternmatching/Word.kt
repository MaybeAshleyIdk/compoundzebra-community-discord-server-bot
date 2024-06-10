package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.wordpatternmatching

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.GeneralCategory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.ScalarValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.UnicodeString
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.contains
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.scalarValues

@JvmInline
public value class Word private constructor(private val string: UnicodeString) {

	init {
		require(this.string.isValid())
	}

	override fun toString(): String {
		return "\"${this.string}\""
	}

	internal companion object {

		fun ofString(unicodeString: UnicodeString): Word? {
			if (!(unicodeString.isValid())) {
				return null
			}

			return Word(unicodeString)
		}
	}
}

private fun UnicodeString.isValid(): Boolean {
	if (this.isEmpty()) return false

	if (!(this[0].isWordBegin())) return false

	if (this.length == 1) return true

	val x = this.substring(1, this.lastIndex)
		.all(ScalarValue::isWordMiddle)
	if (!x) {
		return false
	}

	if (!(this.last().isWordEnd())) return false

	return true
}

internal fun ScalarValue.isWordBegin(): Boolean {
	return ((this in GeneralCategory.Letter) || (this in GeneralCategory.Number))
}

internal fun ScalarValue.isWordMiddle(): Boolean {
	return (this in GeneralCategory.Letter) ||
		(this in GeneralCategory.Number) ||
		(this in GeneralCategory.Punctuation.CONNECTOR) ||
		(this in GeneralCategory.Punctuation.DASH) ||
		(this == ScalarValue.ofChar('\''))
}

internal fun ScalarValue.isWordEnd(): Boolean {
	return (this in GeneralCategory.Letter) ||
		(this in GeneralCategory.Number) ||
		(this in GeneralCategory.Punctuation.CONNECTOR) ||
		(this == ScalarValue.ofChar('\''))
}

private fun List<ScalarValue>.joinScalarValuesToString(): String {
	return this
		.joinToString(separator = "") { scalarValue: ScalarValue ->
			scalarValue.toCodePoint().asString()
		}
}

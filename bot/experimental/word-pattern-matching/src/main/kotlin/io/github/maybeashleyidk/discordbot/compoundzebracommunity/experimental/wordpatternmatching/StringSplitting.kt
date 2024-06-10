package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.wordpatternmatching

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.ScalarValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.UnicodeString
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode.toUnicode

private fun splitStringIntoWords(string: UnicodeString): Sequence<Word> {
	if (string.isEmpty()) {
		return emptySequence()
	}

	return sequence {
		var beginIndex = 0
		var endIndex: Int = string.length

		while ((beginIndex < string.length) && (endIndex > beginIndex)) {
			var wordString: UnicodeString = string.substring(beginIndex, endIndex)
			if ((wordString.length >= 3) && wordString.startsAndEndsWith(ScalarValue.ofChar('\''))) {
				wordString = wordString.substring(1, wordString.lastIndex)
			}
			val word: Word? = Word.ofString(wordString)

			if (word != null) {
				beginIndex = endIndex
				endIndex = string.length

				this@sequence.yield(word)

				continue
			}

			--endIndex

			if (endIndex <= beginIndex) {
				++beginIndex
				endIndex = string.length
			}
		}
	}
}

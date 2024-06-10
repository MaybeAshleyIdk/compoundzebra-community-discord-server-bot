package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.wordpatternmatching

import org.junit.jupiter.api.Test

class Demo {

	@Test
	fun demo() {
		val words = splitStringIntoWords("!(69-)? fo-5--_--+do * 'bar'").toList()
		println(words)
	}
}

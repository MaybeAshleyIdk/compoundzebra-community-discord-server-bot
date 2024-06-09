package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename.NamespaceNameStringValidationResult as ValidationResult

class NamespaceNameStringValidationUnitTests {

	@Test
	fun `validating an empty namespace name string returns an invalid empty`() {
		assertEquals(ValidationResult.Invalid.EMPTY, validateNamespaceNameString(""))
	}
}

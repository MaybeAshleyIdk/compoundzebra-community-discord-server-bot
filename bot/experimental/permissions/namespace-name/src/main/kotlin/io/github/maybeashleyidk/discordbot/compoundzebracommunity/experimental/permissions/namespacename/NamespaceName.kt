package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename.NamespaceNameStringValidationResult as ValidationResult

@JvmInline
public value class NamespaceName private constructor(private val string: String) {

	init {
		require(Companion.isValidString(this.string)) {
			"Invalid permission namespace name string ${this.string.quoted()}"
		}
	}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public fun validateString(nameString: String): ValidationResult {
			val invalidResult: ValidationResult.Invalid? = validateNamespaceNameString(nameString)

			if (invalidResult != null) {
				return invalidResult
			}

			val name = NamespaceName(nameString)
			return ValidationResult.Valid(name)
		}

		public fun isValidString(nameString: String): Boolean {
			return (validateNamespaceNameString(nameString) == null)
		}

		public fun ofStringOrThrow(nameString: String): NamespaceName {
			return when (val result: ValidationResult = this.validateString(nameString)) {
				is ValidationResult.Valid -> result.namespaceName
				is ValidationResult.Invalid -> error(result.message)
			}
		}
	}
}

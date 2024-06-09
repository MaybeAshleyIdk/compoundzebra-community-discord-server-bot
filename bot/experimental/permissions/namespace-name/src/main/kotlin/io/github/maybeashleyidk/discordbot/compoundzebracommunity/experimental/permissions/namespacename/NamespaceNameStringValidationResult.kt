package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename

public sealed interface NamespaceNameStringValidationResult {

	@JvmInline
	public value class Valid(public val namespaceName: NamespaceName) : NamespaceNameStringValidationResult

	public enum class Invalid(internal val message: String) : NamespaceNameStringValidationResult {
		EMPTY("the string must not be empty"),
		INVALID_FIRST_CHAR("the first character must be a basic latin lowercase letter (a–z)"),
		INVALID_MIDDLE_CHAR("the middle characters must be a basic latin letter (A–Z, a–z) or an ASCII digit (0–9)"),
		INVALID_LAST_CHAR("the last character must be a basic latin letter (A–Z, a–z) or an ASCII digit (0–9)"),
	}
}

package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.generic

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.dropLast

@JvmInline
public value class PermissionId private constructor(private val string: String) {

	public constructor(namespaceQualifier: PermissionNamespaceQualifier, name: PermissionName) :
		this("$namespaceQualifier.$name")

	init {
		require(this.string.isValidPermissionId()) {
			"Invalid permission ID string \"${this.string}\""
		}
	}

	public val namespaceQualifier: PermissionNamespaceQualifier
		get() {
			val namespaceQualifierStr: String = this.string.splitToSequence('.')
				.dropLast()
				.joinToString(separator = ".")

			return PermissionNamespaceQualifier.ofStringOrThrow(namespaceQualifierStr)
		}

	public val name: PermissionName
		get() {
			val nameStr: String = this.string.splitToSequence('.')
				.last()

			return PermissionName.ofStringOrThrow(nameStr)
		}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public fun ofStringOrThrow(idString: String): PermissionId {
			require(idString.isValidPermissionId()) {
				"Invalid permission ID string \"$idString\""
			}

			return PermissionId(idString)
		}
	}
}

private fun String.isValidPermissionId(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	val iterator: Iterator<String> = this.reversed().splitToSequence('.').iterator()

	check(iterator.hasNext())
	if (!(iterator.next().isValidPermissionName())) {
		return false
	}

	if (!(iterator.hasNext())) {
		return false
	}
	do {
		if (!(iterator.next().isValidPermissionNamespaceName())) {
			return false
		}
	} while (iterator.hasNext())

	return true
}

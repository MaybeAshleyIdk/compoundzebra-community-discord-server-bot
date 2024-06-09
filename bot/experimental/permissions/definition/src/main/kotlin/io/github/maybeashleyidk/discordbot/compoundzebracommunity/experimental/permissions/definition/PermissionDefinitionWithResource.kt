package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.definition

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.generic.PermissionId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.resource.Resource
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.sequences.dropLast

@JvmInline
public value class PermissionDefinitionWithResource private constructor(private val string: String) :
	PermissionDefinition {

	public constructor(permissionId: PermissionId, resource: Resource) : this("$permissionId($resource)")

	public val id: PermissionId
		get() {
			val idString: String = this.string.splitToSequence(':')
				.dropLast()
				.single()

			return PermissionId.ofStringOrThrow(idString)
		}

	public val resource: Resource
		get() {
			val resourceDefinitionStr: String = this.string.splitToSequence(':')
				.drop(1)
				.single()

			return ResourceDefinition.ofStringOrThrow(resourceDefinitionStr)

			val name = PermissionResourceName.ofStringOrThrow(s)
			return PermissionResourceDefinition(name)
		}

	override fun toString(): String {
		return this.string
	}
}

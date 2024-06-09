package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.definition

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.generic.PermissionId

@JvmInline
public value class PermissionDefinitionWithoutResource(public val permissionId: PermissionId) : PermissionDefinition {

    override fun toString(): String {
        return this.permissionId.toString()
    }
}

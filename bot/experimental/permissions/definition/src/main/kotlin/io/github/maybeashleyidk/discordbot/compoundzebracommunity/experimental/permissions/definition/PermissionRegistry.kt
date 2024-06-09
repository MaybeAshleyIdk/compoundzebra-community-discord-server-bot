package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.definition

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.generic.PermissionId

public interface PermissionRegistry {

	public fun registerPermission(id: PermissionId)
}

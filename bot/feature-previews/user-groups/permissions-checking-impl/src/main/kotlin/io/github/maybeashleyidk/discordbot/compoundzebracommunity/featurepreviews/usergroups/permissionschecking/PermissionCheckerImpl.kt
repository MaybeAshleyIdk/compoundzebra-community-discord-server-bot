package io.github.maybeashleyidk.discordbot.compoundzebracommunity.featurepreviews.usergroups.permissionschecking

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.checking.PermissionChecker
import net.dv8tion.jda.api.entities.User
import javax.inject.Inject

public class PermissionCheckerImpl @Inject constructor() : PermissionChecker {

	override suspend fun checkIsUserPermitted(user: User, permission: Nothing): Boolean {
		TODO("Not yet implemented")
	}
}

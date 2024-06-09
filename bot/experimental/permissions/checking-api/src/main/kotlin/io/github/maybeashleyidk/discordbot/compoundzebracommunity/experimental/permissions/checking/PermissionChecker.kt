package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.checking

import net.dv8tion.jda.api.entities.User

public interface PermissionChecker {

	public suspend fun checkIsUserPermitted(user: User, permission: Nothing): Boolean
}

public interface UserPermissionExpression {

	public val user: User

	public val permission: Nothing
}

public infix fun User.isAllowedTo(permission: Nothing): UserPermissionExpression {}

public suspend fun PermissionChecker.check(expression: UserPermissionExpression): Boolean {
	return this.checkIsUserPermitted(expression.user, expression.permission)
}

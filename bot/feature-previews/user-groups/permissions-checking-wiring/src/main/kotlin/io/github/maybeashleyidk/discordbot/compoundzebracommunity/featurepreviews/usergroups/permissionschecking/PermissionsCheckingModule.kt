package io.github.maybeashleyidk.discordbot.compoundzebracommunity.featurepreviews.usergroups.permissionschecking

import dagger.Binds
import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.checking.PermissionChecker

@Module(includes = [PermissionsCheckingModule.Bindings::class])
public object PermissionsCheckingModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindPermissionChecker(impl: PermissionCheckerImpl): PermissionChecker
	}
}

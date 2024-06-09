package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.internal.AllAllowedProjectDependenciesPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.internal.NoneProjectDependenciesPolicy

public object ProjectDependenciesPolicies {

	public fun allAllowed(): ProjectDependenciesPolicy {
		return AllAllowedProjectDependenciesPolicy
	}

	public fun none(): ProjectDependenciesPolicy {
		return NoneProjectDependenciesPolicy
	}
}

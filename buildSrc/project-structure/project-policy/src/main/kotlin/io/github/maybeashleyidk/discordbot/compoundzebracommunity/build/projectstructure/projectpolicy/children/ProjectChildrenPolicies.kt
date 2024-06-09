package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.internal.MinProjectChildrenPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.internal.NoneProjectChildrenPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType

public object ProjectChildrenPolicies {

	public fun none(): ProjectChildrenPolicy {
		return NoneProjectChildrenPolicy
	}

	public fun required0Recommended2(allowedTypes: Set<ProjectType>): ProjectChildrenPolicy {
		return MinProjectChildrenPolicy(minRequired = 0, minRecommended = 2, allowedTypes)
	}

	public fun required1Recommended2(allowedTypes: Set<ProjectType>): ProjectChildrenPolicy {
		return MinProjectChildrenPolicy(minRequired = 1, minRecommended = 2, allowedTypes)
	}
}

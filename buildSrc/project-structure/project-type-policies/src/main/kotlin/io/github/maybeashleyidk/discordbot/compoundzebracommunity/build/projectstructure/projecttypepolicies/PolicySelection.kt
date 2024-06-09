package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.COMPOSITE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.NAMESPACE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.STANDALONE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service.COMPOSITE_SERVICE_IMPLEMENTATION_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service.SERVICE_INTERFACE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service.SERVICE_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service.SERVICE_WIRING_POLICY
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.service.STANDALONE_SERVICE_IMPLEMENTATION_POLICY

public fun ProjectType.selectPolicy(): ProjectDetailsPolicy {
	return when (this) {
		ProjectType.STANDALONE -> STANDALONE_POLICY

		ProjectType.NAMESPACE -> NAMESPACE_POLICY

		ProjectType.COMPOSITE -> COMPOSITE_POLICY

		ProjectType.SERVICE -> SERVICE_POLICY
		ProjectType.SERVICE_INTERFACE -> SERVICE_INTERFACE_POLICY
		ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> STANDALONE_SERVICE_IMPLEMENTATION_POLICY
		ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> COMPOSITE_SERVICE_IMPLEMENTATION_POLICY
		ProjectType.SERVICE_WIRING -> SERVICE_WIRING_POLICY
	}
}

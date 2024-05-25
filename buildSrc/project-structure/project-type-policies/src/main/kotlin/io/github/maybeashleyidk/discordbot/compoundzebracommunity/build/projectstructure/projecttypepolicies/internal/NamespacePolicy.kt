package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectDetailsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectNamePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.ProjectPluginsPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.SourceCodePolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.dependencies.ProjectDependenciesPolicies
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils.selectEnumValues

private val ALLOWED_CHILD_TYPES: Set<ProjectType> =
	selectEnumValues<ProjectType> { projectType: ProjectType ->
		when (projectType) {
			ProjectType.STANDALONE -> true
			ProjectType.NAMESPACE -> true
			ProjectType.COMPOSITE -> true
			ProjectType.SERVICE -> true
			ProjectType.SERVICE_INTERFACE -> false
			ProjectType.SERVICE_IMPLEMENTATION_STANDALONE -> false
			ProjectType.SERVICE_IMPLEMENTATION_COMPOSITE -> false
			ProjectType.SERVICE_WIRING -> false
		}
	}

internal val NAMESPACE_POLICY: ProjectDetailsPolicy =
	ProjectDetailsPolicy(
		namePolicy = ProjectNamePolicy.AllPermissive,
		pluginsPolicy = ProjectPluginsPolicy.NONE,
		sourceCodePolicy = SourceCodePolicy.NONE,
		dependenciesPolicy = ProjectDependenciesPolicies.none(),
		childrenPolicy = ProjectChildrenPolicies.required0Recommended2(allowedTypes = ALLOWED_CHILD_TYPES),
	)

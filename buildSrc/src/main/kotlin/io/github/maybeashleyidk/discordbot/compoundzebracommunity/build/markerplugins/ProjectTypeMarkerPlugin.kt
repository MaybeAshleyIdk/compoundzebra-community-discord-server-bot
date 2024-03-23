package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.ProjectStructureEnforcementPlugin
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType.CompositeType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType.StandaloneType
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin that marks a project as a certain type.
 */
public sealed class ProjectTypeMarkerPlugin(internal val projectType: ProjectType) : Plugin<Project> {

	override fun apply(target: Project) {
		// TODO: find a way to get rid of this circular-dependency?
		target.pluginManager.apply(ProjectStructureEnforcementPlugin::class.java)
	}
}

public class StandaloneProjectMarkerPlugin : ProjectTypeMarkerPlugin(ProjectType.Standalone(StandaloneType.REGULAR))

public class CompositeProjectMarkerPlugin : ProjectTypeMarkerPlugin(ProjectType.Composite(CompositeType.REGULAR))

public class NamespaceProjectMarkerPlugin : ProjectTypeMarkerPlugin(ProjectType.Namespace)

// region service

public class ServiceProjectMarkerPlugin :
	ProjectTypeMarkerPlugin(ProjectType.Composite(CompositeType.SERVICE))

public class ServiceInterfaceProjectMarkerPlugin :
	ProjectTypeMarkerPlugin(ProjectType.Standalone(StandaloneType.SERVICE_INTERFACE))

public class StandaloneServiceImplementationProjectMarkerPlugin :
	ProjectTypeMarkerPlugin(ProjectType.Standalone(StandaloneType.SERVICE_IMPLEMENTATION))

public class CompositeServiceImplementationProjectMarkerPlugin :
	ProjectTypeMarkerPlugin(ProjectType.Composite(CompositeType.SERVICE_IMPLEMENTATION))

public class ServiceWiringProjectMarkerPlugin :
	ProjectTypeMarkerPlugin(ProjectType.Standalone(StandaloneType.SERVICE_WIRING))

// endregion

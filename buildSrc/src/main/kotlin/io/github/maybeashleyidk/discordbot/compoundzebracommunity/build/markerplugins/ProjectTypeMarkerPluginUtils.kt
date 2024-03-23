package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype.ProjectType
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import kotlin.reflect.KClass

internal fun PluginContainer.findProjectTypeMarkerPlugin(): ProjectTypeMarkerPlugin? {
	val pluginKClasses: Array<KClass<out ProjectTypeMarkerPlugin>> =
		@Suppress("ktlint:standard:no-blank-line-in-list")
		arrayOf(
			StandaloneProjectMarkerPlugin::class,
			CompositeProjectMarkerPlugin::class,
			NamespaceProjectMarkerPlugin::class,

			ServiceProjectMarkerPlugin::class,
			ServiceInterfaceProjectMarkerPlugin::class,
			StandaloneServiceImplementationProjectMarkerPlugin::class,
			CompositeServiceImplementationProjectMarkerPlugin::class,
			ServiceWiringProjectMarkerPlugin::class,
		)

	val plugins: List<ProjectTypeMarkerPlugin> = pluginKClasses
		.mapNotNull { pluginKClass: KClass<out ProjectTypeMarkerPlugin> ->
			this.findPlugin(pluginKClass.java)
		}

	return when (plugins.size) {
		0 -> null
		1 -> plugins[0]
		else -> error("Project has multiple project type marker plugins applied")
	}
}

internal fun PluginContainer.getProjectTypeMarkerPlugin(): ProjectTypeMarkerPlugin {
	return checkNotNull(this.findProjectTypeMarkerPlugin()) {
		"Project has no project type marker plugin applied"
	}
}

internal fun Project.getProjectTypeOrNull(): ProjectType? {
	val projectTypeMarkerPlugin: ProjectTypeMarkerPlugin? = this.plugins.findProjectTypeMarkerPlugin()
	return projectTypeMarkerPlugin?.projectType
}

internal fun Project.getProjectType(): ProjectType {
	val projectTypeMarkerPlugin: ProjectTypeMarkerPlugin = this.plugins.getProjectTypeMarkerPlugin()
	return projectTypeMarkerPlugin.projectType
}

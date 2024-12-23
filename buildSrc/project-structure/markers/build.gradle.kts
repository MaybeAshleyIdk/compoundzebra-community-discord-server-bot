plugins {
	`kotlin-dsl`
}

dependencies {
	implementation(projects.projectStructure.projectType)
	implementation(projects.projectStructure.enforcement)
}

gradlePlugin {
	this@gradlePlugin.plugins {
		data class Info(
			val name: String,
			val className: String,
		)

		val infoList: List<Info> =
			listOf(
				Info("standalone", "StandaloneProjectMarkerPlugin"),
				Info("namespace", "NamespaceProjectMarkerPlugin"),
				Info("composite", "CompositeProjectMarkerPlugin"),
				Info("service", "ServiceProjectMarkerPlugin"),
				Info("service-interface", "ServiceInterfaceProjectMarkerPlugin"),
				Info("service-implementation.standalone", "StandaloneServiceImplementationProjectMarkerPlugin"),
				Info("service-implementation.composite", "CompositeServiceImplementationProjectMarkerPlugin"),
			)

		for (info: Info in infoList) {
			register(info.name) {
				id = "buildSrc.projectStructure.${this@register.name}"
				implementationClass =
					"io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.markers.${info.className}"
			}
		}
	}
}

plugins {
	`kotlin-dsl`
}

kotlin {
	explicitApi()
}

gradlePlugin {
	plugins {
		fun createMarkerPlugin(pluginName: String, className: String) {
			create(pluginName) {
				id = "buildSrc.projectType.$name"
				implementationClass =
					"io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.markerplugins.$className"
			}
		}

		createMarkerPlugin("standalone", "StandaloneProjectMarkerPlugin")
		createMarkerPlugin("composite", "CompositeProjectMarkerPlugin")
		createMarkerPlugin("namespace", "NamespaceProjectMarkerPlugin")

		createMarkerPlugin("service", "ServiceProjectMarkerPlugin")
		createMarkerPlugin("service-interface", "ServiceInterfaceProjectMarkerPlugin")
		createMarkerPlugin("service-implementation.standalone", "StandaloneServiceImplementationProjectMarkerPlugin")
		createMarkerPlugin("service-implementation.composite", "CompositeServiceImplementationProjectMarkerPlugin")
		createMarkerPlugin("service-wiring", "ServiceWiringProjectMarkerPlugin")
	}
}

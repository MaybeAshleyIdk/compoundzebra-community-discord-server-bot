plugins {
	`kotlin-dsl`
}

dependencies {
	api(projects.settingsInclude)
}

gradlePlugin {
	plugins {
		register("projects-structure-system") {
			id = name
			implementationClass =
				"io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.dummysettingsplugin.ProjectsStructureSystemDummySettingsPlugin"
		}
	}
}

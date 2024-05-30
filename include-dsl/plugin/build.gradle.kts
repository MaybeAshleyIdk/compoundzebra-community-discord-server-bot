plugins {
	`kotlin-dsl`
}

dependencies {
	api(projects.dsl.api)

	implementation(projects.dsl.impl)
}

gradlePlugin {
	plugins {
		register("include-dsl") {
			id = name
			implementationClass =
				"io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.plugin.IncludeDslPlugin"
		}
	}
}

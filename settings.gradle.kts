rootProject.name = "CompoundZebra Community Discord Server Bot"

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
	repositories {
		mavenCentral()
	}
}

include(
	":main",

	":bot",

	":bot:features:polls:public",
	":bot:features:polls:impl",

	":bot:commands:public",
	":bot:commands:impl",

	":bot:config:public",
	":bot:config:impl",

	":snowflake",
	":logging",
	":utils",
)

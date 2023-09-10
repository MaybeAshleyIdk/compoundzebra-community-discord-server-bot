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

	// region config
	":bot:config:models",

	":bot:config:serialization:public",
	":bot:config:serialization:impl-json",

	":bot:config:source:public",
	":bot:config:source:impl-file",

	":bot:config:supplier:public",
	":bot:config:supplier:impl-cache",

	":bot:config:cache:public",
	":bot:config:cache:impl-memory",
	// endregion

	":snowflake",
	":logging",
	":utils",
)

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

@Suppress("ktlint:standard:no-blank-line-in-list")
include(
	":utils",
	":logging",
	":snowflake",

	// region config
	":bot:config:models",

	":bot:config:serialization:public",
	":bot:config:serialization:impl-json",

	":bot:config:source:public",
	":bot:config:source:impl-file",

	":bot:config:cache:public",
	":bot:config:cache:impl-memory",

	":bot:config:supplier:public",
	":bot:config:supplier:impl-cache",
	// endregion

	// region features
	":bot:features:emojistats:public",
	":bot:features:emojistats:impl",

	":bot:features:polls:public",
	":bot:features:polls:impl",

	":bot:features:shutdown:public",
	":bot:features:shutdown:impl",

	":bot:features:all-impl",
	// endregion

	// region commands
	":bot:commands:models:name",
	":bot:commands:models:prefix",

	":bot:commands:impl:base",

	":bot:commands:impl:builtins:config",
	":bot:commands:impl:builtins:magic8ball",
	":bot:commands:impl:builtins:polls",
	":bot:commands:impl:builtins:shutdown",
	":bot:commands:impl:builtins",

	":bot:commands:impl",
	// endregion

	":bot",

	":main",
)

// stupid hacky workaround because gradle has problems if multiple modules have the same name
fun ensureProjectNamesAreUniqueRecursively(project: ProjectDescriptor) {
	project.children
		.forEach(::ensureProjectNamesAreUniqueRecursively)

	if (project.buildFile.exists()) {
		project.name = project.path
			.removePrefix(":")
			.replace(':', '-')
	}
}

rootProject.children
	.forEach(::ensureProjectNamesAreUniqueRecursively)

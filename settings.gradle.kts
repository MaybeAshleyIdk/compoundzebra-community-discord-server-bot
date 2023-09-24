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
	":bot:utils",

	":bot:logging:public",
	":bot:logging:impl-stderr",
	":bot:logging:wiring",

	":bot:snowflake",

	// region config
	":bot:config:models",

	":bot:config:serialization:public",
	":bot:config:serialization:impl-json",
	":bot:config:serialization:wiring",

	":bot:config:source:public",
	":bot:config:source:impl-file",
	":bot:config:source:wiring",

	":bot:config:cache:public",
	":bot:config:cache:impl-memory",
	":bot:config:cache:wiring",

	":bot:config:supplier:public",
	":bot:config:supplier:impl-cache",
	":bot:config:supplier:wiring",

	":bot:config:wiring",
	// endregion

	// region features
	":bot:features:emojistats:public",
	":bot:features:emojistats:impl",
	":bot:features:emojistats:wiring",

	":bot:features:polls:public",
	":bot:features:polls:impl",
	":bot:features:polls:wiring",

	// region shutdown
	":bot:features:shutdown:manager:public",
	":bot:features:shutdown:manager:impl-semaphore",
	":bot:features:shutdown:manager:wiring",

	":bot:features:shutdown:request:public",
	":bot:features:shutdown:request:impl-manager",
	":bot:features:shutdown:request:wiring",

	":bot:features:shutdown:wait:public",
	":bot:features:shutdown:wait:impl-manager",
	":bot:features:shutdown:wait:wiring",

	":bot:features:shutdown:wiring",
	// endregion

	":bot:features:all-wiring",
	// endregion

	// region commands
	":bot:commands:models:name",
	":bot:commands:models:prefix",

	":bot:commands:impl:base",

	":bot:commands:impl:builtins:coinflip",
	":bot:commands:impl:builtins:config",
	":bot:commands:impl:builtins:emojistats",
	":bot:commands:impl:builtins:magic8ball",
	":bot:commands:impl:builtins:polls",
	":bot:commands:impl:builtins:rng",
	":bot:commands:impl:builtins:shutdown",
	":bot:commands:impl:builtins:all-wiring",

	":bot:commands:impl",
	// endregion

	":bot:conditionalmessages:impl",
	":bot:conditionalmessages:wiring",

	":bot:models:env",
	":bot:models:token",
	":bot:wiring",
	":bot:main",
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

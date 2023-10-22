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

@Suppress("ktlint:standard:no-blank-line-in-list", "ktlint:standard:no-consecutive-blank-lines")
include(
	":bot:utils",
	":bot:utils-coroutines-jda",


	":bot:env",

	":bot:token",


	":bot:logging:api",
	":bot:logging:impl-stderr",
	":bot:logging:wiring",
	":bot:logging",


	":bot:snowflake",


	":bot:config",

	":bot:config-serialization:api",
	":bot:config-serialization:impl-json",
	":bot:config-serialization:wiring",
	":bot:config-serialization",

	":bot:config-source:api",
	":bot:config-source:impl-file",
	":bot:config-source:wiring",
	":bot:config-source",

	":bot:config-cache:api",
	":bot:config-cache:impl-memory",
	":bot:config-cache:wiring",
	":bot:config-cache",

	":bot:config-supplier:api",
	":bot:config-supplier:impl-cache",
	":bot:config-supplier:wiring",
	":bot:config-supplier",


	":bot:emojistats:api",
	":bot:emojistats:impl",
	":bot:emojistats:wiring",
	":bot:emojistats",


	":bot:polls:api",
	":bot:polls:impl",
	":bot:polls:wiring",
	":bot:polls",


	":bot:shutdown-manager:api",
	":bot:shutdown-manager:impl-semaphore",
	":bot:shutdown-manager:wiring",
	":bot:shutdown-manager",

	":bot:shutdown-wait:api",
	":bot:shutdown-wait:impl-manager",
	":bot:shutdown-wait:wiring",
	":bot:shutdown-wait",

	":bot:shutdown-request:api",
	":bot:shutdown-request:impl-manager",
	":bot:shutdown-request:wiring",
	":bot:shutdown-request",


	":bot:command-name",

	":bot:command-prefix",


	// region commands
	":bot:commands:impl:base",

	":bot:commands:impl:builtins:coinflip",
	":bot:commands:impl:builtins:config",
	":bot:commands:impl:builtins:dev",
	":bot:commands:impl:builtins:emojistats",
	":bot:commands:impl:builtins:magic8ball",
	":bot:commands:impl:builtins:polls",
	":bot:commands:impl:builtins:rng",
	":bot:commands:impl:builtins:shutdown",
	":bot:commands:impl:builtins:all-wiring",

	":bot:commands:impl",
	// endregion

	":bot:conditionalmessages",


	":bot:message-event-handler-mediator:impl",
	":bot:message-event-handler-mediator:wiring",


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

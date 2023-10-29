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


	":bot:environment-type",

	":bot:token",


	":bot:logging:api",
	":bot:logging:impl-stderr",
	":bot:logging:wiring",
	":bot:logging",


	":bot:snowflake",
	":bot:snowflake-generator",


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
	":bot:commands:base",

	":bot:commands:builtins:coinflip",
	":bot:commands:builtins:config",
	":bot:commands:builtins:dev",
	":bot:commands:builtins:emojistats",
	":bot:commands:builtins:magic8ball",
	":bot:commands:builtins:polls",
	":bot:commands:builtins:rng",
	":bot:commands:builtins:shutdown",
	":bot:commands:builtins:all-wiring",

	":bot:commands:echo-command",
	":bot:commands:message-parser",
	":bot:commands:event-handler",
	":bot:commands:wiring",
	// endregion


	":bot:conditional-messages",


	":bot:message-event-handler-mediator:api",
	":bot:message-event-handler-mediator:impl",
	":bot:message-event-handler-mediator:wiring",


	":bot:jda-factory",
	":bot:wiring",
	":bot:main",
	":bot",

	":main",
)

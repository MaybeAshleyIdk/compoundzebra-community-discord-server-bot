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
	":bot:utils-coroutines-jda",

	":bot:environment-type",

	":bot:token",

	":bot:logging:api",
	":bot:logging:impl-stderr",
	":bot:logging:wiring",
	":bot:logging",

	":bot:snowflake",
	":bot:snowflake-generator",

	// region config

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

	// endregion

	":bot:emoji-stats:api",
	":bot:emoji-stats:impl",
	":bot:emoji-stats:wiring",
	":bot:emoji-stats",

	":bot:polls:api",
	":bot:polls:impl",
	":bot:polls:wiring",
	":bot:polls",

	// region shutdown

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

	// endregion

	":bot:command-name",

	":bot:command-prefix",

	// region command message event handler

	":bot:command-message-event-handler:api",

	// region implementation

	":bot:command-message-event-handler:impl:message-parser",
	":bot:command-message-event-handler:impl:command",

	":bot:command-message-event-handler:impl:built-in-commands:coin-flip",
	":bot:command-message-event-handler:impl:built-in-commands:config",
	":bot:command-message-event-handler:impl:built-in-commands:dev",
	":bot:command-message-event-handler:impl:built-in-commands:emoji-stats",
	":bot:command-message-event-handler:impl:built-in-commands:magic8ball",
	":bot:command-message-event-handler:impl:built-in-commands:polls",
	":bot:command-message-event-handler:impl:built-in-commands:rng",
	":bot:command-message-event-handler:impl:built-in-commands:shutdown",
	":bot:command-message-event-handler:impl:built-in-commands",

	":bot:command-message-event-handler:impl:echo-command",
	":bot:command-message-event-handler:impl:core",
	":bot:command-message-event-handler:impl:wiring",
	":bot:command-message-event-handler:impl",

	// endregion

	":bot:command-message-event-handler:wiring",

	":bot:command-message-event-handler",

	// endregion

	":bot:conditional-messages:api",
	":bot:conditional-messages:impl",
	":bot:conditional-messages:wiring",
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

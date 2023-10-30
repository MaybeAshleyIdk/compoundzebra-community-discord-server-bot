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

	// region polls

	":bot:poll-id",
	":bot:poll-description",
	":bot:poll-option",
	":bot:poll-details",

	":bot:poll-management:api",
	":bot:poll-management:impl",
	":bot:poll-management:wiring",
	":bot:poll-management",

	":bot:poll-creation:api",
	":bot:poll-creation:impl-manager",
	":bot:poll-creation:wiring",
	":bot:poll-creation",

	":bot:poll-holding:api",
	":bot:poll-holding:impl-manager",
	":bot:poll-holding:wiring",
	":bot:poll-holding",

	":bot:poll-modification:api",
	":bot:poll-modification:impl-manager",
	":bot:poll-modification:wiring",
	":bot:poll-modification",

	":bot:poll-component-protocol",

	":bot:poll-event-listening:api",
	":bot:poll-event-listening:impl",
	":bot:poll-event-listening:wiring",
	":bot:poll-event-listening",

	// endregion

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

	// region command message event handling

	":bot:command-message-event-handling:api",

	// region implementation

	":bot:command-message-event-handling:impl:message-parser",
	":bot:command-message-event-handling:impl:command",

	":bot:command-message-event-handling:impl:built-in-commands:coin-flip",
	":bot:command-message-event-handling:impl:built-in-commands:config",
	":bot:command-message-event-handling:impl:built-in-commands:dev",
	":bot:command-message-event-handling:impl:built-in-commands:emoji-stats",
	":bot:command-message-event-handling:impl:built-in-commands:magic8ball",
	":bot:command-message-event-handling:impl:built-in-commands:polls",
	":bot:command-message-event-handling:impl:built-in-commands:rng",
	":bot:command-message-event-handling:impl:built-in-commands:shutdown",
	":bot:command-message-event-handling:impl:built-in-commands",

	":bot:command-message-event-handling:impl:predefined-response-command",
	":bot:command-message-event-handling:impl:core",
	":bot:command-message-event-handling:impl:wiring",
	":bot:command-message-event-handling:impl",

	// endregion

	":bot:command-message-event-handling:wiring",

	":bot:command-message-event-handling",

	// endregion

	":bot:conditional-message-event-handling:api",
	":bot:conditional-message-event-handling:impl",
	":bot:conditional-message-event-handling:wiring",
	":bot:conditional-message-event-handling",

	":bot:message-event-handler-mediation:api",
	":bot:message-event-handler-mediation:impl",
	":bot:message-event-handler-mediation:wiring",

	":bot:jda-factory",
	":bot:wiring",
	":bot:main",
	":bot",

	":main",
)

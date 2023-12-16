rootProject.name = "compoundzebra-community-discord-server-bot"

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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

@Suppress("ktlint:standard:no-blank-line-in-list")
include(
	":bot:utils",
	":bot:utils-coroutines",
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

	":bot:event-handler",

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

	":bot:self-timeout:api",
	":bot:self-timeout:impl",
	":bot:self-timeout:wiring",
	":bot:self-timeout",

	// region shutdown

	":bot:shutdown:callbacks",

	":bot:shutdown:management:api",
	":bot:shutdown:management:impl",
	":bot:shutdown:management:wiring",
	":bot:shutdown:management",

	":bot:shutdown:event-handling:api",
	":bot:shutdown:event-handling:impl-manager",
	":bot:shutdown:event-handling:wiring",
	":bot:shutdown:event-handling",

	":bot:shutdown:callback-registration:api",
	":bot:shutdown:callback-registration:impl-manager",
	":bot:shutdown:callback-registration:wiring",
	":bot:shutdown:callback-registration",

	":bot:shutdown:requesting:api",
	":bot:shutdown:requesting:impl-manager",
	":bot:shutdown:requesting:wiring",
	":bot:shutdown:requesting",

	":bot:shutdown:wiring",

	":bot:shutdown",

	// endregion

	// region commands

	// this project isn't called just ":bot:commands:name" because the type-safe project accessors already have
	// a property called `name` and if a project name would be "name" then it would lead to conflicts
	":bot:commands:command-name",

	":bot:commands:prefix",

	// region message event handling

	":bot:commands:message-event-handling:api",

	// region implementation

	":bot:commands:message-event-handling:impl:message-parser",
	":bot:commands:message-event-handling:impl:command",

	":bot:commands:message-event-handling:impl:built-in-commands:coin-flip",
	":bot:commands:message-event-handling:impl:built-in-commands:config",
	":bot:commands:message-event-handling:impl:built-in-commands:dev",
	":bot:commands:message-event-handling:impl:built-in-commands:emoji-stats",
	":bot:commands:message-event-handling:impl:built-in-commands:magic8ball",
	":bot:commands:message-event-handling:impl:built-in-commands:polls",
	":bot:commands:message-event-handling:impl:built-in-commands:rng",
	":bot:commands:message-event-handling:impl:built-in-commands:self-timeout",
	":bot:commands:message-event-handling:impl:built-in-commands:shutdown",
	":bot:commands:message-event-handling:impl:built-in-commands:source-code",
	":bot:commands:message-event-handling:impl:built-in-commands",

	":bot:commands:message-event-handling:impl:predefined-response-command",
	":bot:commands:message-event-handling:impl:core",
	":bot:commands:message-event-handling:impl:wiring",
	":bot:commands:message-event-handling:impl",

	// endregion

	":bot:commands:message-event-handling:wiring",

	":bot:commands:message-event-handling",

	// endregion

	":bot:commands",

	// endregion

	":bot:conditional-message-event-handling:api",
	":bot:conditional-message-event-handling:impl",
	":bot:conditional-message-event-handling:wiring",
	":bot:conditional-message-event-handling",

	":bot:message-event-handler-mediation:api",
	":bot:message-event-handler-mediation:impl",
	":bot:message-event-handler-mediation:wiring",
	":bot:message-event-handler-mediation",

	":bot:private-message-event-handling:api",
	":bot:private-message-event-handling:impl",
	":bot:private-message-event-handling:wiring",
	":bot:private-message-event-handling",

	":bot:event-listening:api",
	":bot:event-listening:impl",
	":bot:event-listening:wiring",
	":bot:event-listening",

	":bot:jda-factory",
	":bot:wiring",
	":bot:main",
	":bot",

	":main",
)

val botCommandsNameProject: ProjectDescriptor = project(":bot:commands:command-name")
botCommandsNameProject.projectDir = botCommandsNameProject.projectDir.parentFile.resolve("name")

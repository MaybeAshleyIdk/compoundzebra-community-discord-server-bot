rootProject.name = "compoundzebra-community-discord-server-bot"

pluginManagement {
	includeBuild(rootProject.projectDir.resolve("include-dsl").toString())

	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
	id("include-dsl")
}

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
	repositories {
		mavenCentral()
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include {
	"bot" {
		"utils" {
			"strings"()

			"coroutines-read-write-mutex"()
			"coroutines-atomic"()

			"coroutines-jda"()

			"event-handling-result"()

			"di"()
		}

		"build-type"()

		"token"()

		"logging".service(impl = "stderr")

		"snowflake"()
		"snowflake-generator"()

		"storage" {
			"database".service(impl = "sqlite")
			"wiring"()
		}

		"config"()
		"config-serialization".service(impl = "json")
		"config-source".service(impl = "file")
		"config-cache".service(impl = "memory")
		"config-supplier".service(impl = "cache")

		"generic-event-handler"()

		"emoji-stats".service()

		"polls" {
			"id"()
			"description"()
			"option"()
			"details"()

			"management".service()
			"creation".service(impl = "manager")
			"holding".service(impl = "manager")
			"modification".service(impl = "manager")

			"component-protocol"()

			"event-handling".service()

			"wiring"()
		}

		"self-timeout".service()

		"shutdown" {
			"callbacks"()

			"management".service()
			"event-handling".service(impl = "manager")
			"callback-registration".service(impl = "manager")
			"requesting".service(impl = "manager")

			"wiring"()
		}

		"commands" {
			"name"()
			"prefix"()

			"message-event-handling".service {
				"message-parser"()
				"command"()

				"built-in-commands" {
					"coin-flip"()
					"config"()
					"dev"()
					"emoji-stats"()
					"magic8ball"()
					"polls"()
					"rng"()
					"self-timeout"()
					"shutdown"()
					"source-code"()
				}
				"built-in-commands-factory"()

				"predefined-response-command"()

				"event-handler-impl"()
				"event-handler-impl-factory"()
			}
		}

		"conditional-message-event-handling".service()

		"message-event-handler-mediation".service()

		"private-message-event-handling".service()

		"event-listening".service()

		"jda-factory"()
		"wiring"()
		"main"()
	}

	"main"()
}

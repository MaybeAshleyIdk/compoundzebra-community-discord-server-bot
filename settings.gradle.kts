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

			"di"()
		}

		"environment-type"()

		"token"()

		"logging".serviceWithoutWiring(impl = "stderr")

		"snowflake"()
		"snowflake-generator"()

		"storage" {
			"database".serviceWithoutWiring(impl = "sqlite")
			"wiring"()
		}

		"config"()
		"config-serialization".serviceWithoutWiring(impl = "json")
		"config-source".serviceWithoutWiring(impl = "file")
		"config-cache".serviceWithoutWiring(impl = "memory")
		"config-supplier".serviceWithoutWiring(impl = "cache")

		"generic-event-handler"()

		"emoji-stats".serviceWithoutWiring()

		"polls" {
			"id"()
			"description"()
			"option"()
			"details"()

			"management".serviceWithoutWiring()
			"creation".serviceWithoutWiring(impl = "manager")
			"holding".serviceWithoutWiring(impl = "manager")
			"modification".serviceWithoutWiring(impl = "manager")

			"component-protocol"()

			"event-handling".serviceWithoutWiring()

			"wiring"()
		}

		"self-timeout".serviceWithoutWiring()

		"shutdown" {
			"callbacks"()

			"management".serviceWithoutWiring()
			"event-handling".serviceWithoutWiring(impl = "manager")
			"callback-registration".serviceWithoutWiring(impl = "manager")
			"requesting".serviceWithoutWiring(impl = "manager")

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
				"built-in-commands-wiring"()

				"predefined-response-command"()

				"event-handler-impl"()
				"wiring"()
			}
		}

		"conditional-message-event-handling".serviceWithoutWiring()

		"message-event-handler-mediation".service()

		"private-message-event-handling".service()

		"event-listening".service()

		"jda-factory"()
		"wiring"()
		"main"()
	}

	"main"()
}

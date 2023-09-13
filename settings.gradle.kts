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
	":main",

	":bot",

	":bot:features:polls:public",
	":bot:features:polls:impl",

	":bot:commands:models:name",
	":bot:commands:models:prefix",
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

// stupid hacky workaround because gradle has problems if multiple modules have the same name
fun renameFinalChildren(project: ProjectDescriptor) {
	if (project.children.isEmpty()) {
		project.name = project.path
			.removePrefix(":")
			.replace(':', '-')

		return
	}

	for (childProject: ProjectDescriptor in project.children) {
		renameFinalChildren(childProject)
	}
}

renameFinalChildren(rootProject)

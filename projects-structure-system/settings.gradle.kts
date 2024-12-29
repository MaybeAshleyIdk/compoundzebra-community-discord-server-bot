rootProject.name = "projects-structure-system"

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
	repositories {
		mavenCentral()
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
	":project-name",
	":project-path",
)

include(
	":service-implementation-name",
	":structured-project-info",
	":include-dsl:api",
	":include-dsl:impl",
	":settings-include",
	":dummy-settings-plugin",
)

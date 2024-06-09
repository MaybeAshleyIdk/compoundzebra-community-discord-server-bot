rootProject.name = "include-dsl"

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
	repositories {
		mavenCentral()
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":project-name")

include(
	":dsl:api",
	":dsl:impl",
)

include(":plugin")

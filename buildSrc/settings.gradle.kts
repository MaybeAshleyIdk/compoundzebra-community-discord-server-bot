rootProject.name = "buildSrc"

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
	repositories {
		mavenCentral()
	}

	versionCatalogs {
		create("libs") {
			from(files(rootProject.projectDir.resolve("../gradle/libs.versions.toml")))
		}
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
)

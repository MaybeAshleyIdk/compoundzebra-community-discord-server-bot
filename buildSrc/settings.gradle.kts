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
	":java-compatibility",
	":conventions",
)

include(
	":project-structure:project-name",
	":project-structure:tree",
	":project-structure:project-type",
	":project-structure:project-policy",
)

include(
	":really-executable-jar",
	":gzip",
)

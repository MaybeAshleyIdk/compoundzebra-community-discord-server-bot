plugins {
	`kotlin-dsl`
}

dependencies {
	val kotlinGradlePluginDependencyProvider: Provider<Any> = libs.versions.kotlin
		.map { kotlinVersion: String ->
			kotlin("gradle-plugin", version = kotlinVersion)
		}

	implementation(kotlinGradlePluginDependencyProvider)
}

kotlin {
	explicitApi()
}

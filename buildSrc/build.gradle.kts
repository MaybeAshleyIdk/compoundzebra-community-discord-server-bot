import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	`kotlin-dsl`
}

allprojects {
	plugins.withType<KotlinDslPlugin> {
		extensions.configure<KotlinJvmProjectExtension> {
			explicitApi()
		}
	}
}

dependencies {
	val kotlinGradlePluginDependencyProvider: Provider<Any> = libs.versions.kotlin
		.map { kotlinVersion: String ->
			kotlin("gradle-plugin", version = kotlinVersion)
		}

	implementation(kotlinGradlePluginDependencyProvider)

	implementation(projects.javaCompatibility)

	api(projects.reallyExecutableJar)
	api(projects.gzip)
}

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	`kotlin-dsl` apply false
}

allprojects {
	plugins.withType<KotlinDslPlugin> {
		extensions.configure<KotlinJvmProjectExtension> {
			explicitApi()
		}
	}
}

dependencies {
	api(projects.conventions)

	api(projects.reallyExecutableJar)
	api(projects.gzip)
}
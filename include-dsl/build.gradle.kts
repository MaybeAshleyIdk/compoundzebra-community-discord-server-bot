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
	api(projects.plugin)
}

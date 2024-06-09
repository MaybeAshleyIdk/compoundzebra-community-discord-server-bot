import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	`kotlin-dsl` apply false
}

allprojects {
	plugins.withType<KotlinDslPlugin> {
		extensions.configure<KotlinJvmProjectExtension> {
			compilerOptions {
				jvmTarget = JvmTarget.JVM_21
			}

			explicitApi()
		}
	}
}

dependencies {
	api(projects.conventions)

	api(projects.projectStructure.markers)
	api(projects.projectStructure.subprojectCreation)

	api(projects.reallyExecutableJar)
	api(projects.gzip)
}

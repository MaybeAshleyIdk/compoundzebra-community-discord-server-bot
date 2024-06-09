import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	`kotlin-dsl`
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
	api(projects.plugin)
}

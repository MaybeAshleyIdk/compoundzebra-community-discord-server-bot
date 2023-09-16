import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

plugins {
	kotlin("jvm") version libs.versions.kotlin apply false
	kotlin("kapt") version libs.versions.kotlin apply false

	alias(libs.plugins.ktlint) apply false
}

val javaCompatibilityVersion: JavaVersion = JavaVersion.VERSION_17

subprojects {
	plugins.withType<ApplicationPlugin> {
		extensions.configure<JavaPluginExtension> {
			sourceCompatibility = javaCompatibilityVersion
			targetCompatibility = javaCompatibilityVersion
			toolchain.languageVersion.set(JavaLanguageVersion.of(javaCompatibilityVersion.majorVersion))
		}

		extensions.configure<KotlinTopLevelExtension> {
			jvmToolchain(javaCompatibilityVersion.majorVersion.toInt())
		}
	}

	plugins.withType<JavaLibraryPlugin> {
		extensions.configure<JavaPluginExtension> {
			sourceCompatibility = javaCompatibilityVersion
			targetCompatibility = javaCompatibilityVersion
			toolchain.languageVersion.set(JavaLanguageVersion.of(javaCompatibilityVersion.majorVersion))
		}

		extensions.configure<KotlinTopLevelExtension> {
			jvmToolchain(javaCompatibilityVersion.majorVersion.toInt())
			explicitApi()
		}
	}
}

inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this@hasPlugin.hasPlugin(T::class)
}

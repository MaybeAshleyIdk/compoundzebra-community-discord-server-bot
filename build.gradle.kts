import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

plugins {
	// TODO: bump this to Kotlin 1.9.0 once Moshi supports it
	kotlin("jvm") version "1.8.22" apply false
	kotlin("kapt") version "1.8.22" apply false

	id("io.github.mfederczuk.ktlint") version "0.1.0-indev05" apply false
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

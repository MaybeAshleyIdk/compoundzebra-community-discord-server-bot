import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
	kotlin("jvm") version libs.versions.kotlin apply false

	alias(libs.plugins.ksp) apply false

	alias(libs.plugins.ktlint) apply false
	alias(libs.plugins.shadow) apply false
}

val javaCompatibilityVersion: JavaVersion = JavaVersion.VERSION_17

subprojects {
	plugins.withType<ApplicationPlugin> {
		configureJavaExtension(project = this@subprojects)

		plugins.withType<KotlinPluginWrapper> {
			extensions.configure<KotlinTopLevelExtension> {
				applyCommonKotlinConfigurations()
			}
		}
	}

	plugins.withType<JavaLibraryPlugin> {
		configureJavaExtension(project = this@subprojects)

		plugins.withType<KotlinPluginWrapper> {
			extensions.configure<KotlinTopLevelExtension> {
				applyCommonKotlinConfigurations()
				explicitApi()
			}
		}
	}

	plugins.withType<KotlinPluginWrapper> {
		check(plugins.hasPlugin<ApplicationPlugin>() || plugins.hasPlugin<JavaLibraryPlugin>()) {
			"Before the Kotlin plugin is applied, either the Java application or Java library plugin must tbe applied"
		}
	}
}

fun configureJavaExtension(project: Project) {
	project.extensions.configure<JavaPluginExtension> {
		sourceCompatibility = javaCompatibilityVersion
		targetCompatibility = javaCompatibilityVersion
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(javaCompatibilityVersion.majorVersion))
		}
	}
}

fun KotlinTopLevelExtension.applyCommonKotlinConfigurations() {
	jvmToolchain(javaCompatibilityVersion.majorVersion.toInt())
}

inline fun <reified T : Plugin<*>> PluginContainer.hasPlugin(): Boolean {
	return this@hasPlugin.hasPlugin(T::class)
}

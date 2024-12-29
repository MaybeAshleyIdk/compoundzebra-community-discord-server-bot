import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import kotlin.reflect.KClass

plugins {
	kotlin("jvm") version embeddedKotlinVersion
	`kotlin-dsl` apply false
}

subprojects {
	plugins.withType<JavaLibraryPlugin> {
		extensions.configure<JavaPluginExtension>("java") {
			targetCompatibility = JavaVersion.VERSION_21
			sourceCompatibility = targetCompatibility
		}
	}

	plugins.withAnyType(listOf(KotlinPluginWrapper::class, KotlinDslPlugin::class)) {
		extensions.configure<KotlinJvmProjectExtension>("kotlin") {
			compilerOptions {
				// <https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin_compiler_arguments>
				// Omitting -Xjvm-default=all because our code isn't used by Java or Groovy code.

				for (arg: String in sequenceOf("-Xsam-conversions=class", "-Xjsr305=strict")) {
					if (arg !in freeCompilerArgs.get()) {
						freeCompilerArgs.add(arg)
					}
				}

				javaParameters = true

				jvmTarget = provider {
					val javaTargetVersion: JavaVersion = extensions.getByName<JavaPluginExtension>("java")
						.targetCompatibility

					JvmTarget.fromTarget(javaTargetVersion.toString())
				}
			}

			explicitApi()
		}
	}
}

fun <T : Any, S : T> DomainObjectCollection<T>.withAnyType(
	classes: Iterable<KClass<out S>>,
	configuration: S.() -> Unit,
) {
	for (kClass: KClass<out S> in classes) {
		this.withType<S, T>(@Suppress("UNCHECKED_CAST") (kClass as KClass<S>), configuration)
	}
}

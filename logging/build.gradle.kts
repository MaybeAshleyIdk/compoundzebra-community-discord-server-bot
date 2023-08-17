plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

val javaCompatibilityVersion: JavaVersion = JavaVersion.VERSION_17

java {
	sourceCompatibility = javaCompatibilityVersion
	targetCompatibility = javaCompatibilityVersion
	toolchain.languageVersion.set(JavaLanguageVersion.of(javaCompatibilityVersion.majorVersion))
}

kotlin {
	jvmToolchain(javaCompatibilityVersion.majorVersion.toInt())
	explicitApi()
}

dependencies {
	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}

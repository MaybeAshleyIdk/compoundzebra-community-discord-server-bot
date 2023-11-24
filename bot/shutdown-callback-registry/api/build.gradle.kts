plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-callbacks"))

	implementation(libs.kotlinx.coroutines.core)
}

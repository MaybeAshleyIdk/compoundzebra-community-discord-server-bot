plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(libs.kotlinx.coroutines.core)
}

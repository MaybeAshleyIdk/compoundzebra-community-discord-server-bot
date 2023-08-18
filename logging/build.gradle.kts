plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}

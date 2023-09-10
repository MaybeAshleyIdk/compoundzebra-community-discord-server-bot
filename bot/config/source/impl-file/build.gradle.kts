plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:config:source:public"))
	implementation(project(":bot:config:models"))
	implementation(project(":bot:config:serialization:public"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")

	implementation("com.squareup.okio:okio:3.5.0")
}

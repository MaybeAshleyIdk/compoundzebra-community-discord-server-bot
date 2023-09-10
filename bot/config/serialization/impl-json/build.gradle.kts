plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:config:serialization:public"))
	implementation(project(":bot:config:models"))
	implementation(project(":bot:commands:public"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")

	implementation("com.squareup.okio:okio:3.5.0")

	implementation("com.squareup.moshi:moshi:1.14.0")
	kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
}

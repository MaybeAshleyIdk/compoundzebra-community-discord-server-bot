plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	implementation(project(":bot:features:polls:impl"))
	implementation(project(":bot:commands:impl"))
	implementation(project(":bot:config:supplier:impl-cache"))
	implementation(project(":bot:config:cache:impl-memory"))
	implementation(project(":bot:config:source:impl-file"))
	implementation(project(":bot:config:serialization:impl-json"))
	implementation(project(":snowflake"))
	implementation(project(":logging"))
	implementation(project(":utils"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("net.dv8tion:JDA:5.0.0-beta.13") {
		exclude(module = "opus-java")
	}

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")

	implementation("com.squareup.moshi:moshi:1.14.0")
}

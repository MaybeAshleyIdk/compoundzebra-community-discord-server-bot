plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:features:polls:public"))
	implementation(project(":bot:config:public"))
	implementation(project(":logging"))
	implementation(project(":snowflake"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("net.dv8tion:JDA:5.0.0-beta.13") {
		exclude(module = "opus-java")
	}

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}

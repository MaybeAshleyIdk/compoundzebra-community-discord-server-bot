plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(project(":bot:config:models"))
	implementation(project(":bot:commands:public"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")
}

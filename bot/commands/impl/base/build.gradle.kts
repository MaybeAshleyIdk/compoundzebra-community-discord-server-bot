plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("net.dv8tion:JDA:5.0.0-beta.13") {
		exclude(module = "opus-java")
	}
}

println(project)

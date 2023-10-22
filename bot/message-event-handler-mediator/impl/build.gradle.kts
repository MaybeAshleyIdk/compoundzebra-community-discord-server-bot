plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:commands:bot-commands-impl"))
	api(project(":bot:bot-conditionalmessages"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}

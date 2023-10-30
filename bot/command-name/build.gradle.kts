plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:utils"))
}

plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	compileOnly(kotlin("stdlib"))
	compileOnly(gradleKotlinDsl())

	api(projects.includeDsl.api)

	implementation(projects.includeDsl.impl)
	implementation(projects.projectPath)
	implementation(projects.structuredProjectInfo)
}

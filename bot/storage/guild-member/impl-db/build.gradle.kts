plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:storage:guild-member:api"))
	api(project(":bot:storage:database"))

	implementation(libs.javax.inject)
}

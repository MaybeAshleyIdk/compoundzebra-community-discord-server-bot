plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(TODO("Standalone service-implementation project must have an API dependency on the corresponding service-interface sibling project"))
}

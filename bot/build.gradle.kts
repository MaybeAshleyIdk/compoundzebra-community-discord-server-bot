plugins {
	`java-library`
}

dependencies {
	api(project(":bot:models:bot-models-env"))
	api(project(":bot:models:bot-models-token"))
	api(project(":bot:bot-main"))
}

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.provideJdaDependencyWithoutOpusJava
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

@Suppress("ObjectPropertyName")
public val Project.`jda-without-opusJava`: Provider<MinimalExternalModuleDependency>
	get() {
		return this.provideJdaDependencyWithoutOpusJava()
	}

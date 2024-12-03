package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl

public interface IncludeDsl {

	public operator fun String.invoke(childrenInclude: IncludeDsl.() -> Unit)

	public operator fun String.invoke()

	public fun String.serviceWithoutWiring() {
		this@serviceWithoutWiring {
			"api"()
			"impl"()
		}
	}

	public fun String.serviceWithoutWiring(impl: String) {
		this@serviceWithoutWiring {
			"api"()
			"impl-$impl"()
		}
	}

	public fun String.serviceWithoutWiring(implInclude: IncludeDsl.() -> Unit) {
		this@serviceWithoutWiring {
			"api"()
			"impl"(implInclude)
		}
	}

	@Deprecated(
		"Dagger wiring projects are going to be removed in favor of manual dependency injection",
		ReplaceWith("serviceWithoutWiring()"),
	)
	public fun String.service() {
		this@service {
			"api"()
			"impl"()
			"wiring"()
		}
	}

	@Deprecated(
		"Dagger wiring projects are going to be removed in favor of manual dependency injection",
		ReplaceWith("serviceWithoutWiring(impl)"),
	)
	public fun String.service(impl: String) {
		this@service {
			"api"()
			"impl-$impl"()
			"wiring"()
		}
	}

	@Deprecated(
		"Dagger wiring projects are going to be removed in favor of manual dependency injection",
		ReplaceWith("serviceWithoutWiring(implInclude)"),
	)
	public fun String.service(implInclude: IncludeDsl.() -> Unit) {
		this@service {
			"api"()
			"impl"(implInclude)
			"wiring"()
		}
	}
}

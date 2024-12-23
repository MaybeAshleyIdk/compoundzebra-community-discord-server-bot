package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.includedsl.dsl

public interface IncludeDsl {

	public operator fun String.invoke(childrenInclude: IncludeDsl.() -> Unit)

	public operator fun String.invoke()

	public fun String.service() {
		this@service {
			"api"()
			"impl"()
		}
	}

	public fun String.service(impl: String) {
		this@service {
			"api"()
			"impl-$impl"()
		}
	}

	public fun String.service(implInclude: IncludeDsl.() -> Unit) {
		this@service {
			"api"()
			"impl"(implInclude)
		}
	}
}

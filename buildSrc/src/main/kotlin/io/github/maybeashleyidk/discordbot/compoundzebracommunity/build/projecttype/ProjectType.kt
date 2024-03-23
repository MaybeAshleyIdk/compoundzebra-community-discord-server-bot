package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype

internal sealed class ProjectType {

	enum class StandaloneType {
		REGULAR,
		SERVICE_INTERFACE,
		SERVICE_IMPLEMENTATION,
		SERVICE_WIRING,
	}

	/**
	 * A standalone project does not have any child projects.
	 */
	data class Standalone(val subtype: StandaloneType) : ProjectType()

	enum class CompositeType {
		REGULAR,
		SERVICE,
		SERVICE_IMPLEMENTATION,
	}

	/**
	 * A composite project itself does not contain any source code, it only has child projects, which
	 * the composite project declares at least one of them as API dependency.
	 *
	 * The child projects are mostly considered "private"; sibling and nephew projects of a composite project should not
	 * declare any of the composite project's children as dependency. (the only exception is wiring projects)
	 */
	data class Composite(val subtype: CompositeType) : ProjectType()

	/**
	 * A namespace project itself does not contain any source code, it only has child projects, which — in contrast to
	 * [composite][Composite] projects — are considered "public" and are also not added as dependencies.
	 * In fact, namespace projects do not have any dependencies and also should not be added as dependency.
	 * As the name suggests, namespace projects only serve as a namespace/group/folder/directory.
	 */
	object Namespace : ProjectType()
}

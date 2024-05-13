package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype

// TODO: allow namespace child projects to declare themselves as public or private?
//       would make more sense then to declare the project type in the settings file when the project is included.
//       a more stronger coupling of settings and the project structure would be pretty interesting

public enum class ProjectType {
	/**
	 * The simplest project type — a standalone project does not have any child projects and has no self-imposed
	 * restrictions on dependencies.
	 */
	STANDALONE,

	/**
	 * A namespace project itself neither contains any source code nor does it declare any dependencies, it only has
	 * child projects.
	 *
	 * Declaring a namespace project as a dependency should not be done, as it doesn't add anything.
	 */
	NAMESPACE,

	/**
	 * A composite project itself does not contain any source code, it only has child projects, which
	 * the composite project declares one or more of them as API dependency.
	 *
	 * The child projects are considered private; parent and sibling projects of the composite project should not
	 * declare its children as dependencies. (DI wiring projects are exceptions)
	 */
	COMPOSITE,

	// region service

	/**
	 * A service project does not contain any source code itself, is a combination of its three child projects:
	 *
	 * * the interface ([SERVICE_INTERFACE])
	 * * the implementation ([SERVICE_IMPLEMENTATION_STANDALONE] or [SERVICE_IMPLEMENTATION_COMPOSITE])
	 * * the DI wiring ([SERVICE_WIRING])
	 *
	 * Similar to a composite project ([COMPOSITE]), a service project declares its interface child project as its only
	 * API dependency.
	 * All three children of a service project are also considered private, just like in a composite project.
	 */
	SERVICE,

	/**
	 * A service-interface project declares the public API of the service it is part of (mostly in the form of
	 * Kotlin/Java interfaces) and is very similar to a standalone project ([STANDALONE]) as it has no children.
	 * In addition, a service-interface project is named `api`.
	 */
	SERVICE_INTERFACE,

	/**
	 * A service-implementation project implements the public API declared by
	 * its service-interface ([SERVICE_INTERFACE]) sibling project.
	 * Said sibling project is declared as an API dependency.
	 * In addition, a service-implementation project is named `impl` or `impl-*`.
	 *
	 * As the name suggests, the service-implementation-standalone variation mirrors a standalone project ([STANDALONE])
	 * in the sense that it has no children.
	 *
	 * @see SERVICE_IMPLEMENTATION_COMPOSITE
	 */
	SERVICE_IMPLEMENTATION_STANDALONE,

	/**
	 * A service-implementation project implements the public API declared by
	 * its service-interface ([SERVICE_INTERFACE]) sibling project.
	 * Said sibling project is declared as an API dependency.
	 * In addition, a service-implementation project is named `impl` or `impl-*`.
	 *
	 * As the name suggests, the service-implementation-composite variation mirrors a composite project ([COMPOSITE]) in
	 * the sense that the project itself contains no source code and only has child projects, which
	 * the service-implementation-composite project declares one or more of them as API dependency.
	 *
	 * @see SERVICE_IMPLEMENTATION_STANDALONE
	 */
	SERVICE_IMPLEMENTATION_COMPOSITE,

	/**
	 * A service-wiring project is for defining the DI wiring of interface to implementation and is very similar to
	 * a standalone project ([STANDALONE]) as it has no children.
	 * In addition, a service-wiring project is named `wiring` and declares
	 * its service-implementation ([SERVICE_IMPLEMENTATION_STANDALONE] or [SERVICE_IMPLEMENTATION_COMPOSITE]) sibling
	 * project as an API dependency.
	 */
	SERVICE_WIRING,

	// endregion
}

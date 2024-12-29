package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl

public interface IncludeDsl {

	public interface IncludedProject {

		public fun inDirectory(dir: String): IncludedProject
	}

	/**
	 * Includes a standalone project.
	 */
	public operator fun String.invoke(): IncludedProject

	/**
	 * Includes a namespace project.
	 */
	public operator fun String.invoke(projectType: Namespace, include: IncludeDsl.() -> Unit): IncludedProject

	/**
	 * Includes a composite project.
	 */
	public operator fun String.invoke(projectType: Composite, include: IncludeDsl.() -> IncludedProject): IncludedProject

	// region service

	/**
	 * Includes a service project, with an unnamed standalone implementation.
	 */
	public operator fun String.invoke(projectType: Service): IncludedProject

	/**
	 * Includes a service project, with a standalone implementation named [impl].
	 */
	public operator fun String.invoke(projectType: Service, impl: String): IncludedProject

	/**
	 * Includes a service project, with an unnamed composite implementation.
	 */
	public operator fun String.invoke(projectType: Service, implInclude: IncludeDsl.() -> IncludedProject): IncludedProject

	// endregion
}

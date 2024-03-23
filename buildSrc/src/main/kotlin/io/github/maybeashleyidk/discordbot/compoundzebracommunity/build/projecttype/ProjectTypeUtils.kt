package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projecttype

internal fun ProjectType.isStandalone(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> true
		is ProjectType.Composite -> false
		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isRegularStandalone(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> when (this.subtype) {
			ProjectType.StandaloneType.REGULAR -> true
			ProjectType.StandaloneType.SERVICE_INTERFACE -> false
			ProjectType.StandaloneType.SERVICE_IMPLEMENTATION -> false
			ProjectType.StandaloneType.SERVICE_WIRING -> false
		}

		is ProjectType.Composite -> false
		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isComposite(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> false
		is ProjectType.Composite -> true
		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isNamespace(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> false
		is ProjectType.Composite -> false
		is ProjectType.Namespace -> true
	}
}

/**
 * A "branch" project — as opposed to a "leaf" project — is a type of project that is expected (or required) to have
 * child projects.
 * Whether these child projects are branches or leafs themselves doesn't matter.
 */
internal fun ProjectType.isBranch(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> false
		is ProjectType.Composite -> true
		is ProjectType.Namespace -> true
	}
}

// region service

internal fun ProjectType.isService(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> false
		is ProjectType.Composite -> when (this.subtype) {
			ProjectType.CompositeType.REGULAR -> false
			ProjectType.CompositeType.SERVICE -> true
			ProjectType.CompositeType.SERVICE_IMPLEMENTATION -> false
		}

		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isServiceInterface(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> when (this.subtype) {
			ProjectType.StandaloneType.REGULAR -> false
			ProjectType.StandaloneType.SERVICE_INTERFACE -> true
			ProjectType.StandaloneType.SERVICE_IMPLEMENTATION -> false
			ProjectType.StandaloneType.SERVICE_WIRING -> false
		}

		is ProjectType.Composite -> false
		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isServiceImplementation(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> when (this.subtype) {
			ProjectType.StandaloneType.REGULAR -> false
			ProjectType.StandaloneType.SERVICE_INTERFACE -> false
			ProjectType.StandaloneType.SERVICE_IMPLEMENTATION -> true
			ProjectType.StandaloneType.SERVICE_WIRING -> false
		}

		is ProjectType.Composite -> when (this.subtype) {
			ProjectType.CompositeType.REGULAR -> false
			ProjectType.CompositeType.SERVICE -> false
			ProjectType.CompositeType.SERVICE_IMPLEMENTATION -> true
		}

		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isServiceWiring(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> when (this.subtype) {
			ProjectType.StandaloneType.REGULAR -> false
			ProjectType.StandaloneType.SERVICE_INTERFACE -> false
			ProjectType.StandaloneType.SERVICE_IMPLEMENTATION -> false
			ProjectType.StandaloneType.SERVICE_WIRING -> true
		}

		is ProjectType.Composite -> false
		is ProjectType.Namespace -> false
	}
}

internal fun ProjectType.isServiceChild(): Boolean {
	return (this.isServiceInterface() || this.isServiceImplementation() || this.isServiceWiring())
}

// endregion

internal fun ProjectType.isNotMaybeWiring(): Boolean {
	return !(this.isMaybeWiring())
}

private fun ProjectType.isMaybeWiring(): Boolean {
	return when (this) {
		is ProjectType.Standalone -> when (this.subtype) {
			ProjectType.StandaloneType.REGULAR -> true
			ProjectType.StandaloneType.SERVICE_INTERFACE -> false
			ProjectType.StandaloneType.SERVICE_IMPLEMENTATION -> false
			ProjectType.StandaloneType.SERVICE_WIRING -> true
		}

		is ProjectType.Composite -> false
		is ProjectType.Namespace -> false
	}
}

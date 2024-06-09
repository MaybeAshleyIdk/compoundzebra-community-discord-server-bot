package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectname.ProjectName

public sealed class ProjectNamePolicy {

	public object AllPermissive : ProjectNamePolicy()

	public data class FixedString(
		val string: ProjectName,
		val isReserved: Boolean,
	) : ProjectNamePolicy()

	public data class Pattern(val pattern: Regex) : ProjectNamePolicy()

	public fun allows(name: ProjectName): Boolean {
		return when (this) {
			is AllPermissive -> true
			is FixedString -> (name == this.string)
			is Pattern -> (this.pattern.matchEntire(name.toString()) != null)
		}
	}

	public fun reserves(name: ProjectName): Boolean {
		return when (this) {
			is AllPermissive -> false
			is FixedString -> (this.isReserved && (name == this.string))
			is Pattern -> (this.pattern.matchEntire(name.toString()) != null)
		}
	}

	public val description: String
		get() {
			return when (this) {
				is AllPermissive -> "N/A"
				is FixedString -> "the exact string \"${this.string}\""
				is Pattern -> "the regex pattern /${this.pattern}/"
			}
		}
}

public fun ProjectName.isAllowedBy(policy: ProjectNamePolicy): Boolean {
	return policy.allows(name = this)
}

public fun ProjectName.isReservedBy(policy: ProjectNamePolicy): Boolean {
	return policy.reserves(name = this)
}

public fun ProjectName.isNotReservedBy(policy: ProjectNamePolicy): Boolean {
	return !(this.isReservedBy(policy))
}

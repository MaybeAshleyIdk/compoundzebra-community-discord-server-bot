package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.resource

@JvmInline
public value class Resource private constructor(private val string: String) {

	public constructor(name: ResourceName, typeName: ResourceTypeName) : this("$name: $typeName")

	public val name: ResourceName
		get() {
			val nameStr: String = this.string.splitToSequence(":")
				.first()

			return ResourceName.ofStringOrThrow(nameStr)
		}

	public val typeName: ResourceTypeName
		get() {
			val typeNameStr: String = this.string.splitToSequence(":")
				.last()
				.trimStart()

			return ResourceTypeName.ofStringOrThrow(typeNameStr)
		}

	override fun toString(): String {
		return this.string
	}
}

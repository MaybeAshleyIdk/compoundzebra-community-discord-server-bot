package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option

@JvmInline
public value class PollOptionLabel private constructor(private val labelString: String) {

	init {
		require(this.labelString.isNotBlank()) {
			"Poll option label string must not be blank"
		}
	}

	override fun toString(): String {
		return this.labelString
	}

	public companion object {

		public fun ofString(labelString: String): PollOptionLabel? {
			if (labelString.isBlank()) {
				return null
			}

			return PollOptionLabel(labelString)
		}
	}
}

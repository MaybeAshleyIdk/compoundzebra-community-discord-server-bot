package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option

@JvmInline
public value class PollOptionLabel private constructor(private val string: String) {

	public companion object {

		public fun ofString(labelString: String): PollOptionLabel {
			require(labelString.isNotBlank()) {
				"Poll option label string must not be blank"
			}

			return PollOptionLabel(labelString)
		}
	}

	init {
		require(this.string.isNotBlank()) {
			"Poll option label string must not be blank"
		}
	}

	override fun toString(): String {
		return this.string
	}
}

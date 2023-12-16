package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option

@JvmInline
public value class PollOptionValue private constructor(private val string: String) {

	public companion object {

		public fun ofString(valueString: String): PollOptionValue {
			require(valueString.isNotEmpty()) {
				"Poll option value string must not be empty"
			}

			return PollOptionValue(valueString)
		}
	}

	init {
		require(this.string.isNotEmpty()) {
			"Poll option value string must not be empty"
		}
	}

	override fun toString(): String {
		return this.string
	}
}
